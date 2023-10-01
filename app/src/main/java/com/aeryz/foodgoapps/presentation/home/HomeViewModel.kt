package com.aeryz.foodgoapps.presentation.home

import androidx.lifecycle.ViewModel
import com.aeryz.foodgoapps.data.repository.ProductRepository
import com.aeryz.foodgoapps.presentation.home.adapter.model.HomeSection

class HomeViewModel(private val repo: ProductRepository) : ViewModel() {
    fun getHomeSectionData(): List<HomeSection> = listOf(
        HomeSection.HeaderSection,
        HomeSection.BannerSection,
        HomeSection.CategoriesSection(repo.getCategories()),
        HomeSection.FoodsSection(repo.getFoods()),
    )
}