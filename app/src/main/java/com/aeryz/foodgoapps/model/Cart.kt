package com.aeryz.foodgoapps.model

data class Cart(
    var id: Int = 0,
    var productId : Int = 0,
    var itemQuantity : Int = 0,
    var itemNotes : String? = null
)
