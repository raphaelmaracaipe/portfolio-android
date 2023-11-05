package br.com.raphaelmaracaipe.uiauth.ui.validCode

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.data.UserRepository
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import br.com.raphaelmaracaipe.uiauth.R
import br.com.raphaelmaracaipe.uiauth.sp.AuthSP
import br.com.raphaelmaracaipe.uiauth.sp.AuthSPImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class ValidCodeViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var mContext: Context
    private lateinit var mValidCodeViewModel: ValidCodeViewModel
    private lateinit var mUserRepository: UserRepository
    private lateinit var authSP: AuthSP

    @Before
    fun setUp() {
        mContext = RuntimeEnvironment.getApplication().applicationContext
        authSP = AuthSPImpl(mContext)

        mUserRepository = mockk()

        mValidCodeViewModel = ValidCodeViewModel(
            mContext,
            mUserRepository,
            authSP
        )
    }

    @Test
    fun `when valid code and return success`() = runBlocking {
        coEvery { mUserRepository.validCode(any()) } returns Unit

        mValidCodeViewModel.sendToServer()
        mValidCodeViewModel.showLoading.observeForever { isLoading ->
            Assert.assertFalse(isLoading)
        }
    }

    @Test
    fun `when valid code but return error of code invalid`() {
        coEvery { mUserRepository.validCode(any()) } throws NetworkException(NetworkCodeEnum.USER_SEND_CODE_INVALID.code)
        mValidCodeViewModel.sendToServer()

        val textString = mContext.getString(R.string.response_error_code_invalid)
        mValidCodeViewModel.msgError.observeForever { text ->
            Assert.assertEquals(textString, text)
        }
    }

    @Test
    fun `when valid code but return error of code general`() {
        coEvery { mUserRepository.validCode(any()) } throws NetworkException(NetworkCodeEnum.ERROR_GENERAL.code)
        mValidCodeViewModel.sendToServer()

        val textString = mContext.getString(R.string.response_error_code_general)
        mValidCodeViewModel.msgError.observeForever { text ->
            Assert.assertEquals(textString, text)
        }
    }

    @Test
    fun `when request again new code and api return success`() {
        coEvery { mUserRepository.sendCode(any()) } returns true
        mValidCodeViewModel.sendAgainToServer()
        mValidCodeViewModel.showLoading.observeForever { isLoading ->
            Assert.assertFalse(isLoading)
        }
    }

    @Test
    fun `when request again new code and api return error`() {
        coEvery { mUserRepository.validCode(any()) } throws NetworkException(NetworkCodeEnum.ERROR_GENERAL.code)
        mValidCodeViewModel.sendAgainToServer()

        val textString = mContext.getString(R.string.code_send_again_invalid)
        mValidCodeViewModel.msgError.observeForever { text ->
            Assert.assertEquals(textString, text)
        }
    }

}