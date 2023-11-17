package com.aeryz.foodgoapps.data.network.firebase.auth

import android.text.TextUtils
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FirebaseAuthDataSourceImplTest {

    @MockK()
    lateinit var firebaseAuth: FirebaseAuth

    private lateinit var authDataSource: FirebaseAuthDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        authDataSource = FirebaseAuthDataSourceImpl(firebaseAuth)
    }

    @After
    fun after() {
        unmockkAll()
    }

    private fun mockTaskVoid(exception: Exception? = null): Task<Void> {
        val task: Task<Void> = mockk(relaxed = true)
        every { task.isComplete } returns true
        every { task.exception } returns exception
        every { task.isCanceled } returns false
        val relaxedVoid: Void = mockk(relaxed = true)
        every { task.result } returns relaxedVoid
        return task
    }

    private fun mockTaskAuthResult(exception: Exception? = null): Task<AuthResult> {
        val task: Task<AuthResult> = mockk(relaxed = true)
        every { task.isComplete } returns true
        every { task.exception } returns exception
        every { task.isCanceled } returns false
        val relaxedVoid: AuthResult = mockk(relaxed = true)
        every { task.result } returns relaxedVoid
        return task
    }

    @Test
    fun getCurrentUser(){
        val mockUser = mockk<FirebaseUser>()
        every { firebaseAuth.currentUser } returns mockUser
        val result = authDataSource.getCurrentUser()
        assertEquals(mockUser, result)
        verify { firebaseAuth.currentUser }
    }

    @Test
    fun `test isLoggedIn when user not null`(){
        val mockUser = mockk<FirebaseUser>()
        every { firebaseAuth.currentUser } returns mockUser
        val result = authDataSource.isLoggedIn()
        assertEquals(result, true)
        verify { firebaseAuth.currentUser }
    }

    @Test
    fun `test isLoggedIn when user null`(){
        every { firebaseAuth.currentUser } returns null
        val result = authDataSource.isLoggedIn()
        assertEquals(result, false)
        verify { firebaseAuth.currentUser }
    }

    @Test
    fun `test logout`() {
        mockkStatic(FirebaseAuth::class)
        every { FirebaseAuth.getInstance() } returns firebaseAuth
        every { firebaseAuth.signOut() } returns Unit
        val result = authDataSource.doLogout()
        assertEquals(result, true)
    }

    @Test
    fun `test send change password request by email`() {
        val fakeMail = "test@mail.com"
        every { firebaseAuth.currentUser?.email } returns fakeMail
        every { firebaseAuth.sendPasswordResetEmail(any()) } returns mockTaskVoid()
        val result = authDataSource.sendChangePasswordRequestByEmail()
        assertEquals(result, true)
        verify { firebaseAuth.sendPasswordResetEmail(any()) }
    }

    @Test
    fun `test update email`() {
        every { firebaseAuth.currentUser?.updateEmail(any()) } returns mockTaskVoid()
        runTest {
            val result = authDataSource.updateEmail("test@mail.com")
            assertEquals(result, true)
            verify { firebaseAuth.currentUser?.updateEmail(any()) }
        }
    }

    @Test
    fun `test update password`() {
        every { firebaseAuth.currentUser?.updatePassword(any()) } returns mockTaskVoid()
        runTest {
            val result = authDataSource.updatePassword("new password")
            assertEquals(result, true)
            verify { firebaseAuth.currentUser?.updatePassword(any()) }
        }
    }

    @Test
    fun `test update profile`() {
        mockkStatic(TextUtils::class)
        coEvery { TextUtils.isEmpty(any()) } returns true
        runTest {
            coEvery { firebaseAuth.currentUser?.updateProfile(any()) } returns mockTaskVoid()
            val result = authDataSource.updateProfile("new name", null)
            assertEquals(result, true)
            coVerify { firebaseAuth.currentUser?.updateProfile(any()) }
        }
    }

    @Test
    fun `test register`() {
        runTest() {
            mockkConstructor(UserProfileChangeRequest.Builder::class)
            every { anyConstructed<UserProfileChangeRequest.Builder>().build() } returns mockk()
            val mockAuthResult = mockTaskAuthResult()
            every { firebaseAuth.createUserWithEmailAndPassword(any(), any()) } returns mockAuthResult
            val mockUser = mockk<FirebaseUser>(relaxed = true)
            every { mockAuthResult.result.user } returns mockUser
            every { mockUser.updateProfile(any()) } returns mockTaskVoid()
            val result = authDataSource.doRegister("full name", "test@mail.com", "password")
            assertEquals(result, true)
        }
    }

    @Test
    fun `test login with email and password`() {
        every { firebaseAuth.signInWithEmailAndPassword(any(), any()) } returns mockTaskAuthResult()
        runTest {
            val result = authDataSource.doLogin("test@mail.com", "password")
            assertEquals(result, true)
            verify { firebaseAuth.signInWithEmailAndPassword(any(), any()) }
        }
    }

}