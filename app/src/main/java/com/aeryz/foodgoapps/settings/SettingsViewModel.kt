package com.aeryz.foodgoapps.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeryz.foodgoapps.data.local.datastore.UserPreferenceDataSource
import kotlinx.coroutines.launch

class SettingsViewModel(private val userPreferenceDataSource: UserPreferenceDataSource) : ViewModel() {

    fun setUserDarkModePref(isUsingDarkMode: Boolean) {
        viewModelScope.launch {
            userPreferenceDataSource.setUserDarkModePref(isUsingDarkMode)
        }
    }

}