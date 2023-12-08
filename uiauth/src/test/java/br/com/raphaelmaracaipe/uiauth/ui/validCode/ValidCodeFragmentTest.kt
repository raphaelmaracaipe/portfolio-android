package br.com.raphaelmaracaipe.uiauth.ui.validCode

import android.os.Build
import android.widget.EditText
import androidx.fragment.app.testing.FragmentScenario
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import br.com.raphaelmaracaipe.TestApplication
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import br.com.raphaelmaracaipe.core.externals.ApiKeysDefault
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.externals.NetworkUtils
import br.com.raphaelmaracaipe.core.externals.SpKeyDefault
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum.*
import br.com.raphaelmaracaipe.uiauth.R
import br.com.raphaelmaracaipe.uiauth.di.AuthUiModule
import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M], application = TestApplication::class)
class ValidCodeFragmentTest {

    private val app: TestApplication = ApplicationProvider.getApplicationContext()
    private val mockNavController = Mockito.mock(NavController::class.java)
    private val mockWebServer = MockWebServer()
    private lateinit var modulesToTest: Module

    @Before
    fun setUp() {
        ShadowLog.stream = System.out

        mockWebServer.start()
        val baseURL = mockWebServer.url("").toString()
        NetworkUtils.URL_TO_MOCK = baseURL

        modulesToTest = module {
            single { KeysDefault("nDHj82ZWov6r4bnu", "30rBgU6kuVSHPNXX") }
            single { ApiKeysDefault("aaa", "aaa") }
            single {
                SpKeyDefault(
                    "AAA",
                    "AAA",
                    "AAA",
                    "AAA",
                    "AAA",
                    "AAA",
                    "AAA",
                    "AAA"
                )
            }
        }
    }

    @Test
    fun `when send code wrong should return error`() {
        val jsonObject = JSONObject()
        jsonObject.put("message", USER_SEND_CODE_INVALID.code)

        mockWebServer.enqueue(
            MockResponse().setResponseCode(400).setBody(jsonObject.toString())
        )

        app.loadModules(listOf(*AuthUiModule.allModule().toTypedArray(), modulesToTest)) {
            fragmentScenario().onFragment { fragment ->
                fragment.view?.let {
                    val etNumber = it.findViewById<EditText>(R.id.etNumber)
                    etNumber.setText("999999")

                    val btnLogin = it.findViewById<CircularProgressButton>(R.id.btnLogin)
                    btnLogin.performClick()
                }
            }
        }
    }

    @Test
    fun `when send code correct should return token`() {
        val token = TokensResponse("refreshToken", "accessToken")
        mockWebServer.enqueue(
            MockResponse().setResponseCode(201).setBody(token.toJSON())
        )

        app.loadModules(listOf(*AuthUiModule.allModule().toTypedArray(), modulesToTest)) {
            fragmentScenario().onFragment { fragment ->
                fragment.view?.let {
                    val etNumber = it.findViewById<EditText>(R.id.etNumber)
                    etNumber.setText("999999")

                    val btnLogin = it.findViewById<CircularProgressButton>(R.id.btnLogin)
                    btnLogin.performClick()
                }
            }
        }
    }

    private fun fragmentScenario(): FragmentScenario<ValidCodeFragment> {
        val scenario = FragmentScenario.launch(ValidCodeFragment::class.java)
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
            mockNavController.setGraph(R.navigation.nav_uiauth)

            fragment.viewLifecycleOwnerLiveData.observeForever {
                if (it != null) {
                    Navigation.setViewNavController(fragment.requireView(), mockNavController)
                }
            }
        }
        return scenario
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}