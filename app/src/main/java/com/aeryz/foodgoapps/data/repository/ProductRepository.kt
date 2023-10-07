package com.aeryz.foodgoapps.data.repository

import com.aeryz.foodgoapps.data.dummy.DummyCategoryDataSource
import com.aeryz.foodgoapps.data.local.database.datasource.ProductDataSource
import com.aeryz.foodgoapps.data.local.database.mapper.toProductList
import com.aeryz.foodgoapps.model.Category
import com.aeryz.foodgoapps.model.Product
import com.aeryz.foodgoapps.utils.ResultWrapper
import com.aeryz.foodgoapps.utils.proceed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface ProductRepository {
    fun getCategories(): List<Category>
    fun getProducts(): Flow<ResultWrapper<List<Product>>>
}

class ProductRepositoryImpl(
    private val productDataSource: ProductDataSource,
    private val dummyCategoryDataSource: DummyCategoryDataSource
) : ProductRepository {
    override fun getCategories(): List<Category> {
        return dummyCategoryDataSource.getCategoriesData()
    }

    override fun getProducts(): Flow<ResultWrapper<List<Product>>> {
        return productDataSource.getAllProducts().map {
            proceed { it.toProductList() }
        }.onStart {
            emit(ResultWrapper.Loading())
            delay(2000)
        }
    }

}