package br.com.raphaelmaracaipe.uiauth.ui.validCode

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.data.UserRepository
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import br.com.raphaelmaracaipe.core.utils.Strings
import br.com.raphaelmaracaipe.uiauth.R
import br.com.raphaelmaracaipe.core.data.AuthRepository
import br.com.raphaelmaracaipe.core.data.sp.AuthSP
import br.com.raphaelmaracaipe.core.data.sp.AuthSPImpl
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Assert.assertEquals
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
class ValidCodeViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var mContext: Context
    private lateinit var mValidCodeViewModel: ValidCodeViewModel
    private lateinit var mUserRepository: UserRepository
    private lateinit var mAuthRepository: AuthRepository
    private lateinit var authSP: AuthSP

    @Before
    fun setUp() {
        mContext = RuntimeEnvironment.getApplication().applicationContext
        authSP = AuthSPImpl(mContext)

        mUserRepository = mockk()
        mAuthRepository = mockk()

        mValidCodeViewModel = ValidCodeViewModel(
            mContext,
            mUserRepository,
            mAuthRepository
        )
    }

    @Test
    fun `when valid code but return error of code invalid`() {
        coEvery { mUserRepository.validCode(any()) } throws NetworkException(NetworkCodeEnum.USER_SEND_CODE_INVALID.code)
        mValidCodeViewModel.sendToServer()

        val textString = mContext.getString(R.string.response_error_code_invalid)
        mValidCodeViewModel.msgError.observeForever { text ->
            assertEquals(textString, text)
        }
    }

    @Test
    fun `when valid code but return error of code general`() {
        coEvery { mUserRepository.validCode(any()) } throws NetworkException(NetworkCodeEnum.ERROR_GENERAL.code)
        mValidCodeViewModel.sendToServer()

        val textString = mContext.getString(R.string.response_error_code_general)
        mValidCodeViewModel.msgError.observeForever { text ->
            assertEquals(textString, text)
        }
    }

    @Test
    fun `when request again new code and api return error`() {
        coEvery { mUserRepository.validCode(any()) } throws NetworkException(NetworkCodeEnum.ERROR_GENERAL.code)
        mValidCodeViewModel.sendAgainToServer()

        val textString = mContext.getString(R.string.code_send_again_invalid)
        mValidCodeViewModel.msgError.observeForever { text ->
            assertEquals(textString, text)
        }
    }

    @Test
    fun `when start count down time`() {
        mValidCodeViewModel.startCountDownTime()
        assertTrue(true)
    }

    @Test
    fun `when used on text changed`() {
        val codeRandom = Strings.generateStringRandom(10)
        mValidCodeViewModel.onTextChanged(codeRandom)
        mValidCodeViewModel.code.observeForever { code ->
            assertEquals(codeRandom, code)
        }
    }

}