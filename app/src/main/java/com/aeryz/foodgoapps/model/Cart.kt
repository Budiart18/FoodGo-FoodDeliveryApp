package com.aeryz.foodgoapps.model

data class Cart(
    var id: Int? = null,
    var productId : String,
    val productName: String,
    val productPrice: Double,
    val productImgUrl: String,
    var itemQuantity : Int = 0,
    var itemNotes : String? = null
)
