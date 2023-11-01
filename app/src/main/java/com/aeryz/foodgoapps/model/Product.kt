package com.aeryz.foodgoapps.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Product(
    val id: Int? = null,
    val productName: String,
    val productDescription: String,
    val productPrice: Double,
    val productFormattedPrice: String,
    val productImageUrl: String,
    val productShopLocation: String
) : Parcelable
