package com.aeryz.foodgoapps.presentation.home.adapter.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductViewModel : ViewModel() {
    private val _layoutManagerType = MutableLiveData<LayoutManagerType>()
    val layoutManagerType: LiveData<LayoutManagerType> get() = _layoutManagerType

    fun setLayoutManagerType(type: LayoutManagerType) {
        _layoutManagerType.value = type
    }
}

enum class LayoutManagerType {
    LINEAR,
    GRID
}