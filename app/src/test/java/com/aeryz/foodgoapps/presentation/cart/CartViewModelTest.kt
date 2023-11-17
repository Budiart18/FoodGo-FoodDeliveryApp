package com.aeryz.foodgoapps.presentation.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aeryz.foodgoapps.data.repository.CartRepository
import com.aeryz.foodgoapps.model.Cart
import com.aeryz.foodgoapps.tools.MainCoroutineRule
import com.aeryz.foodgoapps.tools.getOrAwaitValue
import com.aeryz.foodgoapps.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CartViewModelTest {

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var repository: CartRepository

    private lateinit var viewModel: CartViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        val mockCartList = listOf<Cart>(
            mockk(relaxed = true),
            mockk(relaxed = true),
            mockk(relaxed = true)
        )
        coEvery { repository.getUserCartData() } returns flow {
            emit(
                ResultWrapper.Success(
                    Pair(mockCartList, 15000.0)
                )
            )
        }
        viewModel = spyk(CartViewModel(repository))
        val updateResultMock = flow {
            emit(ResultWrapper.Success(true))
        }
        coEvery { repository.decreaseCart(any()) } returns updateResultMock
        coEvery { repository.increaseCart(any()) } returns updateResultMock
        coEvery { repository.deleteCart(any()) } returns updateResultMock
        coEvery { repository.setCartNotes(any()) } returns updateResultMock
    }

    @Test
    fun `get user cart data`() {
        val result = viewModel.cartList.getOrAwaitValue()
        assertEquals(result.payload?.first?.size, 3)
        assertEquals(result.payload?.second, 15000.0)
    }

    @Test
    fun `test decrease cart`() {
        viewModel.decreaseCart(mockk())
        coVerify { repository.decreaseCart(any()) }
    }

    @Test
    fun `test increase cart`() {
        viewModel.increaseCart(mockk())
        coVerify { repository.increaseCart(any()) }
    }

    @Test
    fun `test remove cart`() {
        viewModel.removeCart(mockk())
        coVerify { repository.deleteCart(any()) }
    }

    @Test
    fun `test user done editing notes`() {
        viewModel.userDoneEditingNotes(mockk())
        coVerify { repository.setCartNotes(any()) }
    }
}