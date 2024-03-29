package com.aeryz.foodgoapps.data.local.database.mapper

import com.aeryz.foodgoapps.data.local.database.entity.CartEntity
import com.aeryz.foodgoapps.model.Cart

fun CartEntity?.toCart() = Cart(
    id = this?.id ?: 0,
    productId = this?.productId ?: 0,
    itemQuantity = this?.itemQuantity ?: 0,
    itemNotes = this?.itemNotes.orEmpty(),
    productPrice = this?.productPrice ?: 0.0,
    productName = this?.productName.orEmpty(),
    productImgUrl = this?.productImgUrl.orEmpty()
)

fun Cart?.toCartEntity() = CartEntity(
    id = this?.id,
    productId = this?.productId ?: 0,
    itemQuantity = this?.itemQuantity ?: 0,
    itemNotes = this?.itemNotes.orEmpty(),
    productPrice = this?.productPrice ?: 0.0,
    productName = this?.productName.orEmpty(),
    productImgUrl = this?.productImgUrl.orEmpty()
)

fun List<CartEntity?>.toCartList() = this.map { it.toCart() }
