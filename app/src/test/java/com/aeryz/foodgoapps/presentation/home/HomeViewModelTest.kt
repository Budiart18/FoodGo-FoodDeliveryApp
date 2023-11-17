package com.aeryz.foodgoapps.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aeryz.foodgoapps.data.local.datastore.UserPreferenceDataSource
import com.aeryz.foodgoapps.data.repository.ProductRepository
import com.aeryz.foodgoapps.tools.MainCoroutineRule
import com.aeryz.foodgoapps.tools.getOrAwaitValue
import com.aeryz.foodgoapps.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class HomeViewModelTest {

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var repository: ProductRepository

    @MockK
    lateinit var userPreferenceDataSource: UserPreferenceDataSource

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coEvery { userPreferenceDataSource.getUserLayoutModePrefFlow() } returns flow {
            emit(1)
        }
        viewModel = spyk(
            HomeViewModel(repository, userPreferenceDataSource),
            recordPrivateCalls = true
        )
    }

    @Test
    fun `test get categories`() {
        every { repository.getCategories() } returns flow {
            emit(
                ResultWrapper.Success(
                    listOf(
                        mockk(relaxed = true),
                        mockk(relaxed = true),
                    )
                )
            )
        }
        val result = viewModel.getCategories()
        assertEquals(result, Unit)
        verify { repository.getCategories() }
    }

    @Test
    fun `test get products`() {
        every { repository.getProducts(any()) } returns flow {
            emit(
                ResultWrapper.Success(
                    listOf(
                        mockk(relaxed = true),
                        mockk(relaxed = true),
                        mockk(relaxed = true),
                    )
                )
            )
        }
        val result = viewModel.getProducts("mie")
        val products = viewModel.products.getOrAwaitValue()
        assertEquals(result, Unit)
        assertEquals(products.payload?.size, 3)
        verify { repository.getProducts(any()) }
    }

    @Test
    fun `test set user layout mode`() {
        coEvery { userPreferenceDataSource.setUserLayoutModePref(any()) } returns Unit
        val result = viewModel.setUserLayoutMode(2)
        assertEquals(result, Unit)
        coVerify { userPreferenceDataSource.setUserLayoutModePref(any()) }
    }

}