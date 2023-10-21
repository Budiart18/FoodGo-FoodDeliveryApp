package com.aeryz.foodgoapps.presentation.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.aeryz.foodgoapps.R
import com.aeryz.foodgoapps.data.local.database.AppDatabase
import com.aeryz.foodgoapps.data.local.database.datasource.CartDataSource
import com.aeryz.foodgoapps.data.local.database.datasource.CartDatabaseDataSource
import com.aeryz.foodgoapps.data.network.api.datasource.FoodGoApiDataSource
import com.aeryz.foodgoapps.data.network.api.service.FoodGoApiService
import com.aeryz.foodgoapps.data.repository.CartRepository
import com.aeryz.foodgoapps.data.repository.CartRepositoryImpl
import com.aeryz.foodgoapps.databinding.ActivityDetailBinding
import com.aeryz.foodgoapps.model.Product
import com.aeryz.foodgoapps.utils.GenericViewModelFactory
import com.aeryz.foodgoapps.utils.proceedWhen
import com.aeryz.foodgoapps.utils.toCurrencyFormat
import com.chuckerteam.chucker.api.ChuckerInterceptor

class DetailActivity : AppCompatActivity() {

    private val binding : ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private val viewModel : DetailViewModel by viewModels {
        val database = AppDatabase.getInstance(this)
        val cartDao = database.cartDao()
        val cartDataSource: CartDataSource = CartDatabaseDataSource(cartDao)
        val chuckerInterceptor = ChuckerInterceptor(this.applicationContext)
        val service = FoodGoApiService.invoke(chuckerInterceptor)
        val apiDataSource = FoodGoApiDataSource(service)
        val repo: CartRepository = CartRepositoryImpl(cartDataSource, apiDataSource)
        GenericViewModelFactory.create(DetailViewModel(intent.extras, repo))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bindProduct(viewModel.product)
        observeData()
        setClickListener(viewModel.product)
    }

    private fun setClickListener(product: Product?) {
        binding.cvBack.setOnClickListener{
            onBackPressed()
        }
        binding.tvShopLocation.setOnClickListener{
            navigateToShopMaps(product)
        }
        binding.ivMinusButton.setOnClickListener{
            viewModel.minus()
        }
        binding.ivPlusButton.setOnClickListener{
            viewModel.add()
        }

        binding.bAddToCart.setOnClickListener {
            val itemNotes = binding.etNotes.text.toString()
            viewModel.addToCart(itemNotes)
        }
    }

    private fun navigateToShopMaps(product: Product?) {
        val gmmIntentUri = Uri.parse("geo:0,0?q=" + product?.productShopLocation)
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        startActivity(mapIntent)
    }

    private fun observeData() {
        viewModel.priceLiveData.observe(this){
            binding.tvFoodPriceLiveData.text = it.toCurrencyFormat()
        }
        viewModel.productCountLiveData.observe(this){
            binding.tvFoodTotal.text = it.toString()
        }
        viewModel.addToCartResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(this, "Add to cart success!", Toast.LENGTH_SHORT).show()
                    finish()
                }, doOnError = {
                    Toast.makeText(this, it.exception?.message.orEmpty(), Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun bindProduct(product: Product?) {
        product?.let {item ->
            binding.ivFoodDetailImg.load(item.productImageUrl){
                crossfade(true)
            }
            binding.tvFoodName.text = item.productName
            binding.tvFoodPrice.text = item.productPrice.toCurrencyFormat()
            binding.tvFoodPriceLiveData.text = item.productPrice.toCurrencyFormat()
            binding.tvFoodDescription.text = item.productDescription
            binding.tvShopLocation.text = item.productShopLocation
        }
    }

    companion object {
        const val EXTRA_PRODUCT = "EXTRA_PRODUCT"

        fun startActivity(context: Context, product: Product) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_PRODUCT,product)
            context.startActivity(intent)
        }
    }
}