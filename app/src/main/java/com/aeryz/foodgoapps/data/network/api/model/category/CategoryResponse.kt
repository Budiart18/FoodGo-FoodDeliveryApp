package com.aeryz.foodgoapps.data.network.api.model.category

import androidx.annotation.Keep
import com.aeryz.foodgoapps.model.Category
import com.google.gson.annotations.SerializedName

@Keep
data class CategoryResponse(
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("nama")
    val name: String?
)

fun CategoryResponse.toCategory() = Category(
    categoryName = this.name.orEmpty(),
    categoryImage = this.imageUrl.orEmpty()
)

fun Collection<CategoryResponse>.toCategoryList() = this.map {
    it.toCategory()
}
