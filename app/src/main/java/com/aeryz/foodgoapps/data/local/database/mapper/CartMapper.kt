package com.aeryz.foodgoapps.data.local.database.mapper

import com.aeryz.foodgoapps.data.local.database.entity.CartEntity
import com.aeryz.foodgoapps.data.local.database.relation.CartProductRelation
import com.aeryz.foodgoapps.model.Cart
import com.aeryz.foodgoapps.model.CartProduct

fun CartEntity?.toCart() = Cart(
    id = this?.id ?: 0,
    productId = this?.productId ?: 0,
    itemQuantity = this?.itemQuantity ?: 0,
    itemNotes = this?.itemNotes.orEmpty()
)

fun Cart?.toCartEntity() = CartEntity(
    id = this?.id,
    productId = this?.productId ?: 0,
    itemQuantity = this?.itemQuantity ?: 0,
    itemNotes = this?.itemNotes.orEmpty()
)

fun CartProductRelation.toCartProduct() = CartProduct(
    cart = this.cart.toCart(),
    product = this.product.toProduct()
)

fun List<CartEntity?>.toCartList() = this.map { it.toCart() }

fun List<CartProductRelation>.toCartProductList() = this.map { it.toCartProduct() }