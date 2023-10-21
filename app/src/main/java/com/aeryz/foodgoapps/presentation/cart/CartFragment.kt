package com.aeryz.foodgoapps.presentation.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.aeryz.foodgoapps.R
import com.aeryz.foodgoapps.data.local.database.AppDatabase
import com.aeryz.foodgoapps.data.local.database.datasource.CartDataSource
import com.aeryz.foodgoapps.data.local.database.datasource.CartDatabaseDataSource
import com.aeryz.foodgoapps.data.network.api.datasource.FoodGoApiDataSource
import com.aeryz.foodgoapps.data.network.api.service.FoodGoApiService
import com.aeryz.foodgoapps.data.repository.CartRepository
import com.aeryz.foodgoapps.data.repository.CartRepositoryImpl
import com.aeryz.foodgoapps.databinding.FragmentCartBinding
import com.aeryz.foodgoapps.model.Cart
import com.aeryz.foodgoapps.presentation.checkout.CheckoutActivity
import com.aeryz.foodgoapps.utils.GenericViewModelFactory
import com.aeryz.foodgoapps.utils.hideKeyboard
import com.aeryz.foodgoapps.utils.proceedWhen
import com.aeryz.foodgoapps.utils.toCurrencyFormat
import com.chuckerteam.chucker.api.ChuckerInterceptor

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    private val viewModel: CartViewModel by viewModels {
        val database = AppDatabase.getInstance(requireContext())
        val cartDao = database.cartDao()
        val cartDataSource: CartDataSource = CartDatabaseDataSource(cartDao)
        val chuckerInterceptor = ChuckerInterceptor(requireContext().applicationContext)
        val service = FoodGoApiService.invoke(chuckerInterceptor)
        val apiDataSource = FoodGoApiDataSource(service)
        val repo: CartRepository = CartRepositoryImpl(cartDataSource, apiDataSource)
        GenericViewModelFactory.create(CartViewModel(repo))
    }

    private val adapter: CartListAdapter by lazy {
        CartListAdapter(object : CartListener {
            override fun onPlusTotalItemCartClicked(cart: Cart) {
                viewModel.increaseCart(cart)
            }

            override fun onMinusTotalItemCartClicked(cart: Cart) {
                viewModel.decreaseCart(cart)
            }

            override fun onRemoveCartClicked(cart: Cart) {
                viewModel.removeCart(cart)
            }

            override fun onUserDoneEditingNotes(cart: Cart) {
                viewModel.userDoneEditingNotes(cart)
                hideKeyboard()
            }
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        observeData()
        setClickListener()
    }

    private fun observeData() {
        viewModel.cartList.observe(viewLifecycleOwner){result ->
            result.proceedWhen (
                doOnSuccess = {
                    binding.rvCart.isVisible = true
                    result.payload?.let { (carts, totalPrice) ->
                        adapter.submitData(carts)
                        binding.tvTotalPrice.text = totalPrice.toCurrencyFormat()
                    }
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                },
                doOnError = {err ->
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = err.exception?.message.orEmpty()
                    binding.layoutState.pbLoading.isVisible = false
                    binding.rvCart.isVisible = false
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.rvCart.isVisible = false
                },
                doOnEmpty = {data ->
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = getString(R.string.text_cart_is_empty)
                    data.payload?.let { (_, totalPrice) ->
                        binding.tvTotalPrice.text = totalPrice.toCurrencyFormat()
                    }
                    binding.layoutState.pbLoading.isVisible = false
                    binding.rvCart.isVisible = false
                }
            )
        }
    }

    private fun setClickListener() {
        binding.btnCheckout.setOnClickListener{
            context?.startActivity(Intent(requireContext(), CheckoutActivity::class.java))
        }
    }

    private fun setupList() {
        binding.rvCart.itemAnimator = null
        binding.rvCart.adapter = adapter
    }

}