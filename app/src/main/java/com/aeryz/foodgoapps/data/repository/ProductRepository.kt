package com.aeryz.foodgoapps.data.repository

import com.aeryz.foodgoapps.data.datasource.dummy.CategoryDataSource
import com.aeryz.foodgoapps.data.datasource.dummy.FoodsDataSource
import com.aeryz.foodgoapps.model.Category
import com.aeryz.foodgoapps.model.Food

interface ProductRepository {
    fun getCategories(): List<Category>
    fun getFoods(): List<Food>
}

class ProductRepositoryImpl(
    private val categoryDataSource: CategoryDataSource,
    private val foodDataSource: FoodsDataSource
) : ProductRepository {

    override fun getCategories(): List<Category> {
        return categoryDataSource.getCategoriesData()
    }

    override fun getFoods(): List<Food> {
        return foodDataSource.getFoodsData()
    }
}