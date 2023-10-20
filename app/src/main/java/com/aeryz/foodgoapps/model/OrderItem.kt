package com.aeryz.foodgoapps.model

import com.aeryz.foodgoapps.data.network.api.model.order.OrderItemRequest

data class OrderItem(
    val notes: String,
    val price: Int,
    val name: String,
    val qty: Int
)

fun OrderItem.toOrderItemRequest() = OrderItemRequest(
    notes, price, name, qty
)