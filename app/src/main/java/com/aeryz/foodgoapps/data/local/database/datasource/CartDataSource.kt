package com.aeryz.foodgoapps.data.local.database.datasource

import com.aeryz.foodgoapps.data.local.database.dao.CartDao
import com.aeryz.foodgoapps.data.local.database.entity.CartEntity
import com.aeryz.foodgoapps.data.local.database.relation.CartProductRelation
import kotlinx.coroutines.flow.Flow

interface CartDataSource {
    fun getAllCarts() : Flow<List<CartProductRelation>>
    fun getCartById(cartId: Int) : Flow<CartProductRelation>
    suspend fun insertCarts(carts: List<CartEntity>)
    suspend fun insertCart(cart: CartEntity) : Long
    suspend fun updateCart(cart: CartEntity) : Int
    suspend fun deleteCart(cart: CartEntity) : Int
    suspend fun deleteAllCarts() : Int
}

class CartDatabaseDataSource(private val cartDao : CartDao) : CartDataSource {
    override fun getAllCarts(): Flow<List<CartProductRelation>> {
        return cartDao.getAllCarts()
    }

    override fun getCartById(cartId: Int): Flow<CartProductRelation> {
        return cartDao.getCartById(cartId)
    }

    override suspend fun insertCarts(carts: List<CartEntity>) {
        return cartDao.insertCarts(carts)
    }

    override suspend fun insertCart(cart: CartEntity) : Long {
        return cartDao.insertCart(cart)
    }

    override suspend fun updateCart(cart: CartEntity): Int {
        return cartDao.updateCart(cart)
    }

    override suspend fun deleteCart(cart: CartEntity): Int {
        return cartDao.deleteCart(cart)
    }

    override suspend fun deleteAllCarts(): Int {
        return cartDao.deleteAllCarts()
    }

}