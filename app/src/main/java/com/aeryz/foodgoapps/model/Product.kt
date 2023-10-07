package com.aeryz.foodgoapps.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int? = null,
    val productName: String,
    val productDescription: String,
    val productPrice: Double,
    val productImageUrl: String,
    val productRating: Double,
    val productShopDistance: Double,
    val productShopLocation: String,
    val productShopUrl: String,
) : Parcelable
