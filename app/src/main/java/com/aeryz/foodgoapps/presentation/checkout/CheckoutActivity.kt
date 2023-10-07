package com.aeryz.foodgoapps.presentation.checkout

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.aeryz.foodgoapps.R
import com.aeryz.foodgoapps.data.local.database.AppDatabase
import com.aeryz.foodgoapps.data.local.database.datasource.CartDataSource
import com.aeryz.foodgoapps.data.local.database.datasource.CartDatabaseDataSource
import com.aeryz.foodgoapps.data.repository.CartRepository
import com.aeryz.foodgoapps.data.repository.CartRepositoryImpl
import com.aeryz.foodgoapps.databinding.ActivityCheckoutBinding
import com.aeryz.foodgoapps.presentation.cart.CartListAdapter
import com.aeryz.foodgoapps.presentation.main.MainActivity
import com.aeryz.foodgoapps.utils.GenericViewModelFactory
import com.aeryz.foodgoapps.utils.proceedWhen
import com.aeryz.foodgoapps.utils.toCurrencyFormat

class CheckoutActivity : AppCompatActivity() {

    private val binding : ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(layoutInflater)
    }

    private val adapter : CartListAdapter by lazy {
        CartListAdapter()
    }

    private val viewModel : CheckoutViewModel by viewModels {
        val database = AppDatabase.getInstance(this)
        val cartDao = database.cartDao()
        val cartDataSource : CartDataSource = CartDatabaseDataSource(cartDao)
        val repo : CartRepository = CartRepositoryImpl(cartDataSource)
        GenericViewModelFactory.create(CheckoutViewModel(repo))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickListener()
        setupList()
        observeData()
    }

    private fun setClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.layoutContent.btnCheckout.setOnClickListener {
            customPopupOrderSuccess()
        }
    }

    private fun customPopupOrderSuccess(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_layout_popup_success)

        val btnBackToHome = dialog.findViewById<Button>(R.id.btn_back_to_home)
        btnBackToHome.setOnClickListener {
            dialog.dismiss()
            deleteCartData()
            backToMainActivity()
        }
        dialog.show()
    }

    private fun backToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun deleteCartData(){
        viewModel.deleteAllCarts()
    }

    private fun observeData() {
        viewModel.cartList.observe(this){result ->
            result.proceedWhen (
                doOnSuccess = {result ->
                    binding.layoutContent.root.isVisible = true
                    binding.layoutContent.rvCart.isVisible = true
                    result.payload?.let { (carts, totalPrice) ->
                        adapter.submitData(carts)
                        binding.layoutContent.tvTotalPrice.text = totalPrice.toCurrencyFormat()
                    }
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                }, doOnError = {err->
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = err.exception?.message.orEmpty()
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutContent.root.isVisible = false
                    binding.layoutContent.rvCart.isVisible = false
                }, doOnEmpty = { data ->
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = getString(R.string.text_cart_is_empty)
                    data.payload?.let { (_, totalPrice) ->
                        binding.layoutContent.tvTotalPrice.text = totalPrice.toCurrencyFormat()
                    }
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutContent.root.isVisible = false
                    binding.layoutContent.rvCart.isVisible = false
                }, doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutContent.root.isVisible = false
                    binding.layoutContent.rvCart.isVisible = false
                }
            )
        }
        viewModel.deleteAllCartsResult.observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(this, "Remove Cart List Success", Toast.LENGTH_SHORT).show()
                },
                doOnError = { err ->
                    Toast.makeText(this, err.exception?.message.orEmpty(), Toast.LENGTH_SHORT).show()
                },
                doOnEmpty = {
                    Toast.makeText(this, "Cart is Empty", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun setupList() {
        binding.layoutContent.rvCart.adapter = adapter
    }
}