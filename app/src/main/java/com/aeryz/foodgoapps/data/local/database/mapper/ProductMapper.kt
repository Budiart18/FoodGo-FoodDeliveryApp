package com.aeryz.foodgoapps.data.local.database.mapper

import com.aeryz.foodgoapps.data.local.database.entity.ProductEntity
import com.aeryz.foodgoapps.model.Product

fun ProductEntity?.toProduct() = Product(
    id = this?.id ?: 0,
    productName = this?.productName.orEmpty(),
    productDescription = this?.productDescription.orEmpty(),
    productPrice = this?.productPrice ?: 0.0,
    productImageUrl = this?.productImageUrl.orEmpty(),
    productRating = this?.productRating ?: 0.0,
    productShopDistance = this?.productShopDistance ?: 0.0,
    productShopLocation = this?.productShopLocation.orEmpty(),
    productShopUrl = this?.productShopUrl.orEmpty()
)

fun Product?.toProductEntity() = ProductEntity(
    productName = this?.productName.orEmpty(),
    productDescription = this?.productDescription.orEmpty(),
    productPrice = this?.productPrice ?: 0.0,
    productImageUrl = this?.productImageUrl.orEmpty(),
    productRating = this?.productRating ?: 0.0,
    productShopDistance = this?.productShopDistance ?: 0.0,
    productShopLocation = this?.productShopLocation.orEmpty(),
    productShopUrl = this?.productShopUrl.orEmpty()
).apply {
    this@toProductEntity?.id?.let {
        this.id = this@toProductEntity.id
    }
}

fun List<ProductEntity?>.toProductList() = this.map { it.toProduct() }
fun List<Product?>.toProductEntity() = this.map { it.toProductEntity() }