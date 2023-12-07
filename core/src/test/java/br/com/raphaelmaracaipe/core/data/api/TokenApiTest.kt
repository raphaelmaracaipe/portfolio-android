package br.com.raphaelmaracaipe.core.data.api

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.TestApplication
import br.com.raphaelmaracaipe.core.data.api.request.TokenRefreshRequest
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, sdk = [Build.VERSION_CODES.M])
class TokenApiTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @Test
    fun `when init model`() = runBlocking {
        val tokenApi: TokenApi = TokenApiImpl()
        val tokensResponse = tokenApi.updateToken(TokenRefreshRequest(""))
        assertEquals(tokensResponse.accessToken, "")
    }

}