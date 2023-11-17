package com.aeryz.foodgoapps.presentation.checkout

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aeryz.foodgoapps.data.repository.CartRepository
import com.aeryz.foodgoapps.data.repository.UserRepository
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

class CheckoutViewModelTest {

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var cartRepository: CartRepository

    @MockK
    lateinit var userRepository: UserRepository

    private lateinit var viewModel: CheckoutViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        val mockCartList = listOf<Cart>(
            mockk(relaxed = true),
            mockk(relaxed = true),
            mockk(relaxed = true)
        )
        coEvery { cartRepository.getUserCartData() } returns flow {
            emit(
                ResultWrapper.Success(
                    Pair(mockCartList, 15000.0)
                )
            )
        }
        viewModel = spyk(CheckoutViewModel(cartRepository, userRepository))
        val updateResultMock = flow {
            emit(ResultWrapper.Success(true))
        }
        coEvery { cartRepository.deleteAllCarts() } returns Unit
        coEvery { cartRepository.createOrder(any(), any(), any()) } returns updateResultMock
    }

    @Test
    fun getCartList() {
        val result = viewModel.cartList.getOrAwaitValue()
        assertEquals(result.payload?.first?.size, 3)
        assertEquals(result.payload?.second, 15000.0)
    }

    @Test
    fun deleteAllCarts() {
        val result = viewModel.deleteAllCarts()
        assertEquals(result, Unit)
        coVerify { cartRepository.deleteAllCarts() }
    }

}