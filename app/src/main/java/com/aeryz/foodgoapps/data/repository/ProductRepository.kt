package com.aeryz.foodgoapps.data.repository

import com.aeryz.foodgoapps.data.network.api.datasource.FoodGoDataSource
import com.aeryz.foodgoapps.data.network.api.model.category.toCategoryList
import com.aeryz.foodgoapps.data.network.api.model.product.toProductList
import com.aeryz.foodgoapps.model.Category
import com.aeryz.foodgoapps.model.Product
import com.aeryz.foodgoapps.utils.ResultWrapper
import com.aeryz.foodgoapps.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getCategories(): Flow<ResultWrapper<List<Category>>>
    fun getProducts(category: String? = null): Flow<ResultWrapper<List<Product>>>
}

class ProductRepositoryImpl(
    private val apiDataSource: FoodGoDataSource
) : ProductRepository {
    override fun getCategories(): Flow<ResultWrapper<List<Category>>> {
        return proceedFlow {
            apiDataSource.getCategories().data?.toCategoryList() ?: emptyList()
        }
    }

    override fun getProducts(category: String?): Flow<ResultWrapper<List<Product>>> {
        return proceedFlow {
            apiDataSource.getProducts(category).data?.toProductList() ?: emptyList()
        }
    }
}
