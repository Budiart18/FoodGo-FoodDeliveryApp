package com.aeryz.foodgoapps.data.repository

import app.cash.turbine.test
import com.aeryz.foodgoapps.data.local.database.datasource.CartDataSource
import com.aeryz.foodgoapps.data.local.database.entity.CartEntity
import com.aeryz.foodgoapps.data.network.api.datasource.FoodGoDataSource
import com.aeryz.foodgoapps.data.network.api.model.order.OrderResponse
import com.aeryz.foodgoapps.model.Cart
import com.aeryz.foodgoapps.model.Product
import com.aeryz.foodgoapps.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import java.lang.IllegalStateException

class CartRepositoryImplTest {

    @MockK
    lateinit var localDatasource: CartDataSource

    @MockK
    lateinit var remoteDataSource: FoodGoDataSource

    private lateinit var repository: CartRepository

    private val fakeCartEntityList = listOf(
        CartEntity(
            id = 1,
            productId = 1,
            productName = "Chicken Burger",
            productPrice = 10000.0,
            productImgUrl = "url",
            itemQuantity = 1,
            itemNotes = "notes"
        ),
        CartEntity(
            id = 2,
            productId = 2,
            productName = "Beef Burger",
            productPrice = 15000.0,
            productImgUrl = "url",
            itemQuantity = 1,
            itemNotes = "notes"
        ),
    )

    private val fakeCart = Cart(
        id = 1,
        productId = 1,
        productName = "burger",
        productPrice = 10000.0,
        productImgUrl = "url",
        itemQuantity = 2,
        itemNotes = "notes"
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = CartRepositoryImpl(localDatasource, remoteDataSource)
    }

    @Test
    fun deleteAllCarts(){
        coEvery { localDatasource.deleteAllCarts() } returns Unit
        runTest {
            val result = repository.deleteAllCarts()
            coVerify { localDatasource.deleteAllCarts() }
            assertEquals(result, Unit)
        }
    }

    @Test
    fun `get user cart data, result loading`(){
        val flowMock = flow {
            emit(fakeCartEntityList)
        }
        every { localDatasource.getAllCarts() } returns flowMock
        runTest {
            repository.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(2101)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Loading)
                verify { localDatasource.getAllCarts() }
            }
        }
    }

    @Test
    fun `get user cart data, result success`(){
        val flowMock = flow {
            emit(fakeCartEntityList)
        }
        every { localDatasource.getAllCarts() } returns flowMock
        runTest {
            repository.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Success)
                assertEquals(result.payload?.first?.size, 2)
                assertEquals(result.payload?.second, 25000.0)
                verify { localDatasource.getAllCarts() }
            }
        }
    }

    @Test
    fun `get user cart data, result empty`(){
        val fakeEmptyList = emptyList<CartEntity>()
        val flowMock = flow {
            emit(fakeEmptyList)
        }
        every { localDatasource.getAllCarts() } returns flowMock
        runTest {
            repository.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Empty)
                assertTrue(result.payload?.first?.isEmpty() == true)
                verify { localDatasource.getAllCarts() }
            }
        }
    }

    @Test
    fun `get user cart data, result error`(){
        every { localDatasource.getAllCarts() } returns flow {
            throw IllegalStateException("mock error")
        }
        runTest {
            repository.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Error)
                verify { localDatasource.getAllCarts() }
            }
        }
    }

    @Test
    fun `create cart loading`() {
        runTest {
            val mockProduct = mockk<Product>(relaxed = true)
            coEvery { localDatasource.insertCart(any()) } returns 1
            repository.createCart(mockProduct, 1, "notes")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(110)
                    val result = expectMostRecentItem()
                    assertTrue(result is ResultWrapper.Loading)
                    coVerify { localDatasource.insertCart(any()) }
                }
        }
    }
    @Test
    fun `create cart success, product id not null`() {
        runTest {
            val mockProduct = mockk<Product>(relaxed = true)
            coEvery { localDatasource.insertCart(any()) } returns 1
            repository.createCart(mockProduct, 2, "notes")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val result = expectMostRecentItem()
                    assertTrue(result is ResultWrapper.Success)
                    assertEquals(result.payload, true)
                    coVerify { localDatasource.insertCart(any()) }
            }
        }
    }

    @Test
    fun `create cart error, product id not null`() {
        runTest {
            val mockProduct = mockk<Product>(relaxed = true)
            coEvery { localDatasource.insertCart(any()) } throws IllegalStateException("mock error")
            repository.createCart(mockProduct, 2, "notes")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val result = expectMostRecentItem()
                    assertTrue(result is ResultWrapper.Error)
                    coVerify { localDatasource.insertCart(any()) }
                }
        }
    }

    @Test
    fun `create cart error, product id null`() {
        runTest {
            val mockProduct = mockk<Product>(relaxed = true) {
                every { id } returns null
            }
            coEvery { localDatasource.insertCart(any()) } returns 1
            repository.createCart(mockProduct, 2, "notes")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val result = expectMostRecentItem()
                    assertTrue(result is ResultWrapper.Error)
                    assertEquals(result.exception?.message, "Product ID not found")
                    coVerify(atLeast = 0) { localDatasource.insertCart(any()) }
                }
        }
    }

    @Test
    fun `increase cart`() {
        runTest {
            coEvery { localDatasource.updateCart(any()) } returns 1
            repository.increaseCart(fakeCart)
                .map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val result = expectMostRecentItem()
                    assertTrue(result is ResultWrapper.Success)
                    assertEquals(result.payload, true)
                    coVerify { localDatasource.updateCart(any()) }
                }
        }
    }

    @Test
    fun `decrease cart when quantity less than or equal 0`() {
        val fakeCart = Cart(
            id = 1,
            productId = 1,
            productName = "burger",
            productPrice = 10000.0,
            productImgUrl = "url",
            itemQuantity = 0,
            itemNotes = "notes"
        )
        coEvery { localDatasource.deleteCart(any()) } returns 1
        coEvery { localDatasource.updateCart(any()) } returns 1
        runTest {
            repository.decreaseCart(fakeCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val result = expectMostRecentItem()
                assertEquals(result.payload, true)
                coVerify(atLeast = 1) { localDatasource.deleteCart(any()) }
                coVerify(atLeast = 0) { localDatasource.updateCart(any()) }
            }
        }
    }

    @Test
    fun `decrease cart when quantity more than 0`() {
        val fakeCart = Cart(
            id = 1,
            productId = 1,
            productName = "Sate",
            productPrice = 12000.0,
            productImgUrl = "url",
            itemQuantity = 2,
            itemNotes = "notes"
        )
        coEvery { localDatasource.deleteCart(any()) } returns 1
        coEvery { localDatasource.updateCart(any()) } returns 1
        runTest {
            repository.decreaseCart(fakeCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val result = expectMostRecentItem()
                assertEquals(result.payload, true)
                coVerify(atLeast = 0) { localDatasource.deleteCart(any()) }
                coVerify(atLeast = 1) { localDatasource.updateCart(any()) }
            }
        }
    }

    @Test
    fun `set cart notes success`(){
        coEvery { localDatasource.updateCart(any()) } returns 1
        runTest {
            repository.setCartNotes(fakeCart)
                .map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val result = expectMostRecentItem()
                    assertTrue(result is ResultWrapper.Success)
                    assertEquals(result.payload, true)
                    coVerify { localDatasource.updateCart(any()) }
                }
        }
    }

    @Test
    fun `create order success`() {
        val fakeOrderResponse = OrderResponse(
            code = 200,
            message = "success",
            status = true
        )
        val fakeListCart = listOf(fakeCart)
        val totalPrice = 10000
        val username = "test user"
        runTest {
            coEvery { remoteDataSource.createOrder(any()) } returns fakeOrderResponse
            repository.createOrder(fakeListCart, totalPrice, username)
                .map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val result = expectMostRecentItem()
                    assertTrue(result is ResultWrapper.Success)
                    coVerify { remoteDataSource.createOrder(any()) }
                }
        }
    }

    @Test
    fun `create order error`() {
        val fakeListCart = listOf(fakeCart)
        val totalPrice = 10000
        val username = "test user"
        runTest {
            coEvery { remoteDataSource.createOrder(any()) } throws IllegalStateException("error")
            repository.createOrder(fakeListCart, totalPrice, username).map {
                delay(100)
                it
            }.test {
                delay(210)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Error)
                coVerify { remoteDataSource.createOrder(any()) }
            }
        }
    }

    @Test
    fun `delete cart success`() {
        coEvery { localDatasource.deleteCart(any()) } returns 1
        runTest {
            repository.deleteCart(fakeCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Success)
                assertEquals(result.payload, true)
                coVerify { localDatasource.deleteCart(any()) }
            }
        }
    }

}