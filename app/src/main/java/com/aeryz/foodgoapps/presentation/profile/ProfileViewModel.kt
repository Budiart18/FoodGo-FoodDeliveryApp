package com.aeryz.foodgoapps.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel : ViewModel() {

    private val _isEditModeEnabled = MutableLiveData<Boolean>()

    val isEditModeEnabled: LiveData<Boolean>
        get() = _isEditModeEnabled

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val phoneNumber = MutableLiveData<String>()

    init {
        _isEditModeEnabled.value = false
    }

    fun toggleEditMode() {
        _isEditModeEnabled.value = _isEditModeEnabled.value?.not()
    }

    fun submitProfile() {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {

            }
        }
    }

}