package com.aeryz.foodgoapps.presentation.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aeryz.foodgoapps.R
import com.aeryz.foodgoapps.databinding.ActivityDetailBinding
import com.aeryz.foodgoapps.model.Food
import com.aeryz.foodgoapps.utils.GenericViewModelFactory
import com.aeryz.foodgoapps.utils.toCurrencyFormat

class DetailActivity : AppCompatActivity() {

    private val binding : ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private val viewModel : DetailViewModel by viewModels {
        GenericViewModelFactory.create(DetailViewModel(intent.extras))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bindProduct(viewModel.product)
        observeData()
        setClickListener(viewModel.product)
    }

    private fun setClickListener(product: Food?) {
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
            addFoodToCart(product)
        }
    }

    private fun addFoodToCart(product: Food?) {
        Toast.makeText(this, "${product?.foodName} Added to cart!", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToShopMaps(product: Food?) {
        val url = product?.foodShopUrl
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun observeData() {
        viewModel.priceLiveData.observe(this){
            binding.tvFoodPriceLiveData.text = it.toCurrencyFormat()
        }
        viewModel.productCountLiveData.observe(this){
            binding.tvFoodTotal.text = it.toString()
        }
    }

    private fun bindProduct(product: Food?) {
        product?.let {item ->
            binding.ivFoodDetailImg.setImageResource(item.foodImage)
            binding.tvFoodName.text = item.foodName
            binding.tvFoodPrice.text = item.foodPrice.toCurrencyFormat()
            binding.tvFoodPriceLiveData.text = item.foodPrice.toCurrencyFormat()
            binding.tvFoodDescription.text = item.foodDescription
            binding.tvFoodShopDistance.text = getString(R.string.text_shop_distance_format,item.foodShopDistance)
            binding.tvFoodRating.text = getString(R.string.text_food_reviews_format,item.foodRating)
            binding.tvShopLocation.text = item.foodShopLocation
        }
    }

    companion object {
        const val EXTRA_PRODUCT = "EXTRA_PRODUCT"

        fun startActivity(context: Context, product: Food) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_PRODUCT,product)
            context.startActivity(intent)
        }
    }
}