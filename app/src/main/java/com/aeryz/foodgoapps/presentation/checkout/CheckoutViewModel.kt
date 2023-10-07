package com.aeryz.foodgoapps.presentation.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.aeryz.foodgoapps.data.repository.CartRepository
import com.aeryz.foodgoapps.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckoutViewModel(private val cartRepository: CartRepository) : ViewModel() {

    val cartList = cartRepository.getUserCartData().asLiveData(Dispatchers.IO)

    private val _deleteAllCartsResult = MutableLiveData<ResultWrapper<Boolean>>()
    val deleteAllCartsResult: LiveData<ResultWrapper<Boolean>> get() = _deleteAllCartsResult

    fun deleteAllCarts() {
        viewModelScope.launch {
            cartRepository.deleteAllCarts().collect {
                _deleteAllCartsResult.postValue(it)
            }
        }
    }

}