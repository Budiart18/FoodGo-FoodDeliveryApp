package com.aeryz.foodgoapps.presentation.splashscreen

import androidx.lifecycle.ViewModel
import com.aeryz.foodgoapps.data.repository.UserRepository

class SplashViewModel(private val repository: UserRepository) : ViewModel() {

    fun isUserLoggedIn() = repository.isLoggedIn()
}
