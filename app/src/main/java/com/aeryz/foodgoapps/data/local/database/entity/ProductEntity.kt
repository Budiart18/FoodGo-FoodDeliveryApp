package com.aeryz.foodgoapps.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "product_name")
    val productName: String,
    @ColumnInfo(name = "product_description")
    val productDescription: String,
    @ColumnInfo(name = "product_price")
    val productPrice: Double,
    @ColumnInfo(name = "product_image_url")
    val productImageUrl: String,
    @ColumnInfo(name = "product_rating")
    val productRating: Double,
    @ColumnInfo(name = "product_shop_distance")
    val productShopDistance: Double,
    @ColumnInfo(name = "product_shop_location")
    val productShopLocation: String,
    @ColumnInfo(name = "product_shop_url")
    val productShopUrl: String
)