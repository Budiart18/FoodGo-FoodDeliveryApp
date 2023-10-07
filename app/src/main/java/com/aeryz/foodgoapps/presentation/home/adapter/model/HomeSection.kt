package com.aeryz.foodgoapps.presentation.home.adapter.model

import com.aeryz.foodgoapps.model.Category
import com.aeryz.foodgoapps.model.Product
import com.aeryz.foodgoapps.presentation.home.adapter.HomeAdapter
import com.aeryz.foodgoapps.utils.ResultWrapper


sealed class HomeSection(val id : Int) {
    data object HeaderSection : HomeSection(HomeAdapter.ITEM_TYPE_HEADER)
    data object BannerSection : HomeSection(HomeAdapter.ITEM_TYPE_BANNER)
    data class CategoriesSection(val data : List<Category>) : HomeSection(HomeAdapter.ITEM_TYPE_CATEGORY_LIST)
    data class ProductSection(val data : ResultWrapper<List<Product>>) : HomeSection(HomeAdapter.ITEM_TYPE_PRODUCT_LIST)
}