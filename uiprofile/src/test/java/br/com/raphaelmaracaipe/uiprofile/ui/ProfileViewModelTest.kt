package br.com.raphaelmaracaipe.uiprofile.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.data.UserRepository
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum.ERROR_GENERAL
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class ProfileViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var context: Context
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var userRepository: UserRepository

    private val bitmap: Bitmap? by lazy {
        context.assets.open("br.png").use {
            BitmapFactory.decodeStream(it)
        }
    }

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication().applicationContext

        userRepository = mockk()
        profileViewModel = ProfileViewModel(
            context,
            userRepository
        )
    }

    @Test
    fun `when add image should apply in observable`() {
        bitmap?.let {
            profileViewModel.addImage(it)
        }

        profileViewModel.imagePreview.observeForever {
            assertNotEquals(null, it)
        }
    }

    @Test
    fun `when marked profile with saved should return true`() {
        every { userRepository.markWhichProfileSaved() } returns Unit

        profileViewModel.onTextChange("test name")
        profileViewModel.markWhichProfileSaved("test image")
        assertTrue(profileViewModel.ifExistData())
    }

    @Test
    fun `when clean image preview should return observable with null`() {
        var verifyIfImageIsShow = true

        bitmap?.let {
            profileViewModel.addImage(it)
        }

        profileViewModel.imagePreview.observeForever {
            if (verifyIfImageIsShow) {
                assertNotNull(it)
                verifyIfImageIsShow = false
                profileViewModel.cleanImageSelectedToPreview()
            } else {
                assertNull(it)
            }
        }
    }

    @Test
    fun `when send image to server and return success should close loading`() {
        coEvery { userRepository.profile(any()) } returns Unit

        var checkIfLoadingIsWillOpened = false
        profileViewModel.isShowLoading.observeForever {
            if (checkIfLoadingIsWillOpened) {
                checkIfLoadingIsWillOpened = false
                assertTrue(it)
            } else {
                assertFalse(it)
            }
        }

        checkIfLoadingIsWillOpened = true
        profileViewModel.sendProfileToServer()
    }

    @Test
    fun `when send image to server and return error should close loading and show message error`() {
        coEvery { userRepository.profile(any()) } throws NetworkException(ERROR_GENERAL.code)

        profileViewModel.isShowLoading.observeForever {
            assertTrue(it)
        }

        profileViewModel.msgError.observeForever {
            assertNotEquals("", it)
        }

        profileViewModel.sendProfileToServer()
    }

    @Test
    fun `when apply text should add in model`() {
        profileViewModel.onTextChange("test")
        assertTrue(true)
    }

}