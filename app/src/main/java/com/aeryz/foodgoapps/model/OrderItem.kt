package com.aeryz.foodgoapps.model

import com.aeryz.foodgoapps.data.network.api.model.order.OrderItemRequest

data class OrderItem(
    val notes: String,
    val price: Int,
    val name: String,
    val qty: Int
)

fun OrderItem.toOrderItemRequest() = OrderItemRequest(
    notes = this.notes,
    price = this.price,
    name = this.name,
    qty = this.qty
)

fun Collection<OrderItem>.toOrderItemRequestList() = this.map { it.toOrderItemRequest() }