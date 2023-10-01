package com.aeryz.foodgoapps.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeryz.foodgoapps.data.datasource.local.datastore.UserPreferenceDataSource
import kotlinx.coroutines.launch

class SettingsViewModel(private val userPreferenceDataSource: UserPreferenceDataSource) : ViewModel() {

    fun setUserGridModePref(isUsingGridMode: Boolean) {
        viewModelScope.launch {
            userPreferenceDataSource.setUserGridModePref(isUsingGridMode)
        }
    }

}