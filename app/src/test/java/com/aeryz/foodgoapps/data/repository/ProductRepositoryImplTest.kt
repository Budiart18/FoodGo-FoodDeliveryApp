package com.aeryz.foodgoapps.data.repository

import app.cash.turbine.test
import com.aeryz.foodgoapps.data.network.api.datasource.FoodGoDataSource
import com.aeryz.foodgoapps.data.network.api.model.category.CategoriesResponse
import com.aeryz.foodgoapps.data.network.api.model.category.CategoryResponse
import com.aeryz.foodgoapps.data.network.api.model.product.ProductItemResponse
import com.aeryz.foodgoapps.data.network.api.model.product.ProductsResponse
import com.aeryz.foodgoapps.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import java.lang.IllegalStateException

class ProductRepositoryImplTest {

    @MockK
    lateinit var apiDataSource: FoodGoDataSource

    private lateinit var repository: ProductRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = ProductRepositoryImpl(apiDataSource)
    }

    @Test
    fun `get categories, with result loading`(){
        val mockCategoriesResponse = mockk<CategoriesResponse>()
        runTest {
            coEvery { apiDataSource.getCategories() } returns mockCategoriesResponse
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(110)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Loading)
                coVerify { apiDataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get categories, with result success`(){
        val fakeCategoryResponse = CategoryResponse(
            id = 1,
            name = "burger",
            imageUrl = "url"
        )
        val fakeCategoriesResponse = CategoriesResponse(
            code = 200,
            data = listOf(fakeCategoryResponse),
            message = "success",
            status = true
        )
        runTest {
            coEvery { apiDataSource.getCategories() } returns fakeCategoriesResponse
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(210)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Success)
                assertEquals(result.payload?.size, 1)
                assertEquals(result.payload?.get(0)?.id, 1)
                coVerify { apiDataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get categories, with result empty`(){
        val fakeCategoriesResponse = CategoriesResponse(
            code = 200,
            data = emptyList(),
            message = "empty",
            status = true
        )
        runTest {
            coEvery { apiDataSource.getCategories() } returns fakeCategoriesResponse
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(210)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Empty)
                assertEquals(result.payload?.size, 0)
                coVerify { apiDataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get categories, with result error`(){
        runTest {
            coEvery { apiDataSource.getCategories() } throws IllegalStateException("mock error")
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(250)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Error)
                coVerify { apiDataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get products, with result loading`(){
        val mockProductResponse = mockk<ProductsResponse>()
        runTest {
            coEvery { apiDataSource.getProducts(any()) } returns mockProductResponse
            repository.getProducts("burger").map {
                delay(100)
                it
            }.test {
                delay(110)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Loading)
                coVerify { apiDataSource.getProducts(any()) }

            }
        }
    }

    @Test
    fun `get products, with result success`(){
        val fakeProductItemResponse = ProductItemResponse(
            id = 1,
            address = "address",
            detail = "detail",
            price = 1.0,
            formattedPrice = "1.0",
            imageUrl = "imageUrl",
            name = "name"
        )
        val fakeProductsResponse = ProductsResponse(
            code = 200,
            data = listOf(fakeProductItemResponse),
            message = "success",
            status = true
        )
        runTest {
            coEvery { apiDataSource.getProducts(any()) } returns fakeProductsResponse
            repository.getProducts("burger").map {
                delay(100)
                it
            }.test {
                delay(210)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Success)
                assertEquals(result.payload?.size, 1)
                assertEquals(result.payload?.get(0)?.id, 1)
                coVerify { apiDataSource.getProducts(any()) }
            }
        }
    }

    @Test
    fun `get products, with result empty`(){
        val fakeProductsResponse = ProductsResponse(
            code = 200,
            data = emptyList(),
            message = "empty",
            status = true
        )
        runTest {
            coEvery { apiDataSource.getProducts(any()) } returns fakeProductsResponse
            repository.getProducts("burger").map {
                delay(100)
                it
            }.test {
                delay(210)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Empty)
                assertEquals(result.payload?.size, 0)
                coVerify { apiDataSource.getProducts(any()) }
            }
        }
    }

    @Test
    fun `get products, with result error`(){
        runTest {
            coEvery { apiDataSource.getProducts(any()) } throws IllegalStateException("mock error")
            repository.getProducts("burger").map {
                delay(100)
                it
            }.test {
                delay(210)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Error)
                coVerify { apiDataSource.getProducts(any()) }
            }
        }
    }
}