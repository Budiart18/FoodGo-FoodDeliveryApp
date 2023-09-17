package com.aeryz.foodgoapps.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food(
    val foodName : String,
    val foodDescription : String,
    val foodPrice : String,
    val foodImage : Int,
    val foodRating : String,
    val foodShopDistance : String,
    val foodShopLocation : String,
) : Parcelable
