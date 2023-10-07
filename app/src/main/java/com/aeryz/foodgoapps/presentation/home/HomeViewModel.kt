package com.aeryz.foodgoapps.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aeryz.foodgoapps.data.repository.ProductRepository
import com.aeryz.foodgoapps.model.Product
import com.aeryz.foodgoapps.presentation.home.adapter.model.HomeSection
import com.aeryz.foodgoapps.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map

class HomeViewModel(private val repo: ProductRepository) : ViewModel() {

    val homeData : LiveData<List<HomeSection>>
        get() = repo.getProducts().map {
            mapToHomeData(it)
        }.asLiveData(Dispatchers.IO)

    private fun mapToHomeData(productResult : ResultWrapper<List<Product>>): List<HomeSection> = listOf(
        HomeSection.HeaderSection,
        HomeSection.BannerSection,
        HomeSection.CategoriesSection(repo.getCategories()),
        HomeSection.ProductSection(productResult),
    )
}