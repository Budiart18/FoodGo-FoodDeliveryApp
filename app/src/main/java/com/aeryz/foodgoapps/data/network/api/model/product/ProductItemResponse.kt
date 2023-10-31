package com.aeryz.foodgoapps.data.network.api.model.product

import androidx.annotation.Keep
import com.aeryz.foodgoapps.model.Product
import com.google.gson.annotations.SerializedName

@Keep
data class ProductItemResponse(
    @SerializedName("alamat_resto")
    val address: String?,
    @SerializedName("detail")
    val detail: String?,
    @SerializedName("harga")
    val price: Double?,
    @SerializedName("harga_format")
    val formattedPrice: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("nama")
    val name: String?
)

fun ProductItemResponse.toProduct() = Product(
    productName = this.name.orEmpty(),
    productDescription = this.detail.orEmpty(),
    productPrice = this.price ?: 0.0,
    productFormattedPrice = this.formattedPrice.orEmpty(),
    productImageUrl = this.imageUrl.orEmpty(),
    productShopLocation = this.address.orEmpty()
)

fun Collection<ProductItemResponse>.toProductList() = this.map { it.toProduct() }
