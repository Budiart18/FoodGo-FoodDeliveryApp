package com.aeryz.foodgoapps.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Food(
    val foodId: String = UUID.randomUUID().toString(),
    val foodName: String,
    val foodDescription: String,
    val foodPrice: Double,
    val foodImage: Int,
    val foodRating: Double,
    val foodShopDistance: Double,
    val foodShopLocation: String,
    val foodShopUrl: String
) : Parcelable
