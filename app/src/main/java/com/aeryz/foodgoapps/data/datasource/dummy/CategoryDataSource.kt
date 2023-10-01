package com.aeryz.foodgoapps.data.datasource.dummy

import com.aeryz.foodgoapps.R
import com.aeryz.foodgoapps.model.Category

interface CategoryDataSource {
    fun getCategoriesData() : List<Category>
}

class CategoryDataSourceImpl : CategoryDataSource {
    override fun getCategoriesData(): List<Category> {
        return mutableListOf(
            Category(R.drawable.iv_chicken_cat, "Chicken"),
            Category(R.drawable.iv_burger_cat, "Burger"),
            Category(R.drawable.iv_noodles_cat,"Noodle"),
            Category(R.drawable.iv_dessert_cat,"Dessert"),
            Category(R.drawable.iv_pizza_cat,"Pizza"),
            Category(R.drawable.iv_drink_cat,"Drink"),
        )
    }
}