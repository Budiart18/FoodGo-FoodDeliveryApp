package com.aeryz.foodgoapps.presentation.home.adapter.model

import com.aeryz.foodgoapps.model.Category
import com.aeryz.foodgoapps.model.Food


sealed class HomeSection {
    data object HeaderSection : HomeSection()
    data object BannerSection : HomeSection()
    data class CategoriesSection(val data : List<Category>) : HomeSection()
    data class FoodsSection(val data : List<Food>) : HomeSection()
}