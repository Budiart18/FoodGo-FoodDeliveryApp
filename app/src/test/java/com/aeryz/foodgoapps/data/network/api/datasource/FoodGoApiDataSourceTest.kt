package com.aeryz.foodgoapps.data.network.api.datasource

import com.aeryz.foodgoapps.data.network.api.model.category.CategoriesResponse
import com.aeryz.foodgoapps.data.network.api.model.order.OrderRequest
import com.aeryz.foodgoapps.data.network.api.model.order.OrderResponse
import com.aeryz.foodgoapps.data.network.api.model.product.ProductsResponse
import com.aeryz.foodgoapps.data.network.api.service.FoodGoApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class FoodGoApiDataSourceTest {

    @MockK
    lateinit var service: FoodGoApiService

    private lateinit var dataSource: FoodGoDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = FoodGoApiDataSource(service)
    }

    @Test
    fun getProducts() {
        runTest {
            val mockResponse = mockk<ProductsResponse>(relaxed = true)
            coEvery { service.getProducts(any()) } returns mockResponse
            val result = dataSource.getProducts("burger")
            coVerify { service.getProducts(any()) }
            assertEquals(result, mockResponse)
        }
    }

    @Test
    fun getCategories() {
        runTest {
            val mockResponse = mockk<CategoriesResponse>(relaxed = true)
            coEvery { service.getCategories() } returns mockResponse
            val result = dataSource.getCategories()
            coVerify { service.getCategories() }
            assertEquals(result, mockResponse)
        }
    }

    @Test
    fun createOrder() {
        runTest {
            val mockResponse = mockk<OrderResponse>(relaxed = true)
            val mockRequest = mockk<OrderRequest>(relaxed = true)
            coEvery { service.createOrder(any()) } returns mockResponse
            val result = dataSource.createOrder(mockRequest)
            coVerify { service.createOrder(any()) }
            assertEquals(result, mockResponse)
        }
    }
}