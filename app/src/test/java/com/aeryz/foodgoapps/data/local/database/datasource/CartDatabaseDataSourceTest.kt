package com.aeryz.foodgoapps.data.local.database.datasource

import app.cash.turbine.test
import com.aeryz.foodgoapps.data.local.database.dao.CartDao
import com.aeryz.foodgoapps.data.local.database.entity.CartEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class CartDatabaseDataSourceTest {

    @MockK
    lateinit var cartDao: CartDao

    private lateinit var cartDataSource: CartDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        cartDataSource = CartDatabaseDataSource(cartDao)
    }

    @Test
    fun getAllCarts() {
        val mockCartEntity1 = mockk<CartEntity>()
        val mockCartEntity2 = mockk<CartEntity>()
        val listEntityMock = listOf(mockCartEntity1, mockCartEntity2)
        val flowMock = flow {
            emit(listEntityMock)
        }
        coEvery { cartDao.getAllCarts() } returns flowMock
        runTest {
            cartDataSource.getAllCarts().test {
                val result = awaitItem()
                assertEquals(listEntityMock, result)
                assertEquals(listEntityMock.size, result.size)
                assertEquals(listEntityMock[0], result[0])
                assertEquals(listEntityMock[1], result[1])
                awaitComplete()
            }
        }
    }

    @Test
    fun getCartById() {
        val mockCartEntity = mockk<CartEntity>()
        val flowMock = flow {
            emit(mockCartEntity)
        }
        coEvery { cartDao.getCartById(any()) } returns flowMock
        runTest {
            cartDataSource.getCartById(1).test {
                assertEquals(mockCartEntity, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun insertCarts() {
    }

    @Test
    fun insertCart() {
        val mockCartEntity = mockk<CartEntity>()
        coEvery { cartDao.insertCart(any()) } returns 1
        runTest {
            val result = cartDataSource.insertCart(mockCartEntity)
            coVerify { cartDao.insertCart(any()) }
            assertEquals(result, 1)
        }
    }

    @Test
    fun updateCart() {
        val mockCartEntity = mockk<CartEntity>()
        coEvery { cartDao.updateCart(any()) } returns 1
        runTest {
            val result = cartDataSource.updateCart(mockCartEntity)
            coVerify { cartDao.updateCart(any()) }
            assertEquals(result, 1)
        }
    }

    @Test
    fun deleteCart() {
        val mockCartEntity = mockk<CartEntity>()
        coEvery { cartDao.deleteCart(any()) } returns 1
        runTest {
            val result = cartDataSource.deleteCart(mockCartEntity)
            coVerify { cartDao.deleteCart(any()) }
            assertEquals(result, 1)
        }
    }

    @Test
    fun deleteAllCarts() {
        coEvery { cartDao.deleteAllCarts() } returns Unit
        runTest {
            val result = cartDataSource.deleteAllCarts()
            coVerify { cartDao.deleteAllCarts() }
            assertEquals(result, Unit)
        }
    }
}