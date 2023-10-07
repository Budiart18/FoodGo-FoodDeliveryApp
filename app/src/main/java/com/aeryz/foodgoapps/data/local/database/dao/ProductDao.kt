package com.aeryz.foodgoapps.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aeryz.foodgoapps.data.local.database.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM PRODUCTS")
    fun getAllProducts() : Flow<List<ProductEntity>>

    @Query("SELECT * FROM PRODUCTS WHERE id == :id")
    fun getProductById(id: Int) : Flow<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Update
    suspend fun updateProduct(product: ProductEntity) : Int

    @Delete
    suspend fun deleteProduct(product: ProductEntity) : Int
}