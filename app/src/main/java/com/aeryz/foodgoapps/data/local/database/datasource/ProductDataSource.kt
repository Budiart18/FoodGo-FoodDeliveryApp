package com.aeryz.foodgoapps.data.local.database.datasource

import com.aeryz.foodgoapps.data.local.database.dao.ProductDao
import com.aeryz.foodgoapps.data.local.database.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

interface ProductDataSource {
    fun getAllProducts() : Flow<List<ProductEntity>>
    fun getProductById(id: Int) : Flow<ProductEntity>
    suspend fun insertProducts(products: List<ProductEntity>)
    suspend fun updateProduct(product: ProductEntity) : Int
    suspend fun deleteProduct(product: ProductEntity) : Int
}

class ProductDatabaseDataSource(private val dao : ProductDao) : ProductDataSource {
    override fun getAllProducts(): Flow<List<ProductEntity>> {
        return dao.getAllProducts()
    }

    override fun getProductById(id: Int): Flow<ProductEntity> {
        return dao.getProductById(id)
    }

    override suspend fun insertProducts(products: List<ProductEntity>) {
        return dao.insertProducts(products)
    }

    override suspend fun updateProduct(product: ProductEntity): Int {
        return dao.updateProduct(product)
    }

    override suspend fun deleteProduct(product: ProductEntity): Int {
        return dao.deleteProduct(product)
    }

}