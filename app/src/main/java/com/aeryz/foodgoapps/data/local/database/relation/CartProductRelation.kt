package com.aeryz.foodgoapps.data.local.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.aeryz.foodgoapps.data.local.database.entity.CartEntity
import com.aeryz.foodgoapps.data.local.database.entity.ProductEntity

data class CartProductRelation(
    @Embedded
    val cart: CartEntity,
    @Relation(parentColumn = "product_id", entityColumn = "id")
    val product: ProductEntity
)
