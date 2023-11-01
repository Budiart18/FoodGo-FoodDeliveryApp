package com.aeryz.foodgoapps.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Category(
    val id: Int? = null,
    val categoryImage: String,
    val categoryName: String
) : Parcelable
