package br.com.raphaelmaracaipe.uiauth.ui.validCode

import android.os.Build
import android.widget.EditText
import br.com.raphaelmaracaipe.core.data.AuthRepository
import br.com.raphaelmaracaipe.core.data.CountryRepository
import br.com.raphaelmaracaipe.core.data.KeyRepository
import br.com.raphaelmaracaipe.core.data.UserRepository
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import br.com.raphaelmaracaipe.core.data.di.RepositoryModule
import br.com.raphaelmaracaipe.core.data.sp.DeviceIdSP
import br.com.raphaelmaracaipe.core.data.sp.KeySP
import br.com.raphaelmaracaipe.core.data.sp.ProfileSP
import br.com.raphaelmaracaipe.core.data.sp.SeedSP
import br.com.raphaelmaracaipe.core.data.sp.TokenSP
import br.com.raphaelmaracaipe.core.data.sp.di.SpModule
import br.com.raphaelmaracaipe.core.externals.NetworkUtils
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum.USER_SEND_CODE_INVALID
import br.com.raphaelmaracaipe.tests.fragments.FragmentTest
import br.com.raphaelmaracaipe.uiauth.R
import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import io.mockk.MockKAnnotations
import io.mockk.mockk
import io.mockk.unmockkAll
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.M],
    application = HiltTestApplication::class
)
@UninstallModules(SpModule::class, RepositoryModule::class)
class ValidCodeFragmentTest : FragmentTest() {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @BindValue
    @JvmField
    var deviceIdSP: DeviceIdSP = mockk(relaxed = true)

    @BindValue
    @JvmField
    var tokenSP: TokenSP = mockk(relaxed = true)

    @BindValue
    @JvmField
    var profileSP: ProfileSP = mockk(relaxed = true)

    @BindValue
    @JvmField
    var keySP: KeySP = mockk(relaxed = true)

    @BindValue
    @JvmField
    var seedSP: SeedSP = mockk(relaxed = true)

    @BindValue
    @JvmField
    var keyRepository: KeyRepository = mockk(relaxed = true)

    @BindValue
    @JvmField
    var userRepository: UserRepository = mockk(relaxed = true)

    @BindValue
    @JvmField
    var countryRepository: CountryRepository = mockk(relaxed = true)

    @BindValue
    @JvmField
    var authRepository: AuthRepository = mockk(relaxed = true)

    private val mockWebServer = MockWebServer()

    @Before
    fun setUp() {
        hiltRule.inject()
        MockKAnnotations.init(this)

        ShadowLog.stream = System.out

        mockWebServer.start()
        val baseURL = mockWebServer.url("").toString()
        NetworkUtils.URL_TO_MOCK = baseURL
    }

    @Test
    fun `when send code wrong should return error`() {
        val jsonObject = JSONObject()
        jsonObject.put("message", USER_SEND_CODE_INVALID.code)

        mockWebServer.enqueue(
            MockResponse().setResponseCode(400).setBody(jsonObject.toString())
        )

        fragmentScenario<ValidCodeFragment>(
            R.navigation.nav_uiauth
        ) { fragment ->
            fragment.view?.let {
                val etNumber = it.findViewById<EditText>(R.id.etNumber)
                etNumber.setText("999999")

                val btnLogin = it.findViewById<CircularProgressButton>(R.id.btnLogin)
                btnLogin.performClick()
            }
        }
    }

    @Test
    fun `when send code correct should return token`() {
        val token = TokensResponse("refreshToken", "accessToken")
        mockWebServer.enqueue(
            MockResponse().setResponseCode(201).setBody(token.toJSON())
        )

        fragmentScenario<ValidCodeFragment>(
            R.navigation.nav_uiauth
        ) { fragment ->
            fragment.view?.let {
                val etNumber = it.findViewById<EditText>(R.id.etNumber)
                etNumber.setText("999999")

                val btnLogin = it.findViewById<CircularProgressButton>(R.id.btnLogin)
                btnLogin.performClick()
            }
        }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}