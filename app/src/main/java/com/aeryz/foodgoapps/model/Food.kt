package com.aeryz.foodgoapps.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food(
    val foodImage : Int,
    val foodName : String,
    val foodPrice : String,
    val foodRating : String,
    val foodRestoDistance : String
) : Parcelable
