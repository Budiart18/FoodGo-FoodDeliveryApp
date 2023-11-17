package com.aeryz.foodgoapps.data.local.datastore

import com.aeryz.foodgoapps.utils.PreferenceDataStoreHelper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class UserPreferenceDataSourceImplTest {

    @MockK
    lateinit var preferenceHelper: PreferenceDataStoreHelper

    private lateinit var userPreference: UserPreferenceDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        userPreference = UserPreferenceDataSourceImpl(preferenceHelper)
    }

    @Test
    fun getUserDarkModePref() {
        runTest {
            coEvery { preferenceHelper.getFirstPreference(any(), false) } returns true
            val result = userPreference.getUserDarkModePref()
            assertTrue(result)
            coVerify { preferenceHelper.getFirstPreference(any(), false) }
        }
    }

    @Test
    fun getUserDarkModePrefFlow() {
        runTest {
            every { preferenceHelper.getPreference(any(), false) } returns flow {
                emit(true)
            }
            val result = userPreference.getUserDarkModePrefFlow()
            assertTrue(result.first())
            verify { preferenceHelper.getPreference(any(), false) }
        }
    }

    @Test
    fun setUserDarkModePref() {
        runTest {
            coEvery { preferenceHelper.putPreference(any(), true) } returns Unit
            val result = userPreference.setUserDarkModePref(true)
            assertEquals(result, Unit)
            coVerify { preferenceHelper.putPreference(any(), true) }
        }
    }

    @Test
    fun getUserLayoutModePrefFlow() {
        runTest {
            every { preferenceHelper.getPreference(any(), 1) } returns flow {
                emit(1)
            }
            val result = userPreference.getUserLayoutModePrefFlow()
            assertEquals(result.first(), 1)
            verify { preferenceHelper.getPreference(any(), 1) }
        }
    }

    @Test
    fun setUserLayoutModePref() {
        runTest {
            coEvery { preferenceHelper.putPreference(any(), 1) } returns Unit
            val result = userPreference.setUserLayoutModePref(1)
            assertEquals(result, Unit)
            coVerify { preferenceHelper.putPreference(any(), 1) }
        }
    }
}