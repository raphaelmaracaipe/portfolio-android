package br.com.raphaelmaracaipe.uiauth

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import br.com.raphaelmaracaipe.core.network.ConfigurationRetrofitUtils
import br.com.raphaelmaracaipe.uiauth.di.AuthUiModule
import br.com.raphaelmaracaipe.uiauth.ui.auth.AuthFragment
import br.com.raphaelmaracaipe.uiauth.ui.auth.AuthFragmentDirections
import br.com.raphaelmaracaipe.uiauth.ui.countries.CountriesFragment
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import br.com.raphaelmaracaipe.core.R as CoreR


@RunWith(AndroidJUnit4::class)
@LargeTest
class UserLoginNavigationApp {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val mockWebServer = MockWebServer()
    private lateinit var mContext: Context
    private lateinit var scenario: FragmentScenario<AuthFragment>
    private lateinit var navController: NavController

    @Before
    fun setUp() {
        mockWebServer.start(8555)
        ConfigurationRetrofitUtils.URL_TO_MOCK = mockWebServer.url("").toString()
        mContext = InstrumentationRegistry.getInstrumentation().targetContext

        startKoin {
            androidLogger()
            androidContext(mContext)
            loadKoinModules(AuthUiModule.allModule())
        }

        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.setGraph(R.navigation.nav_uiauth)

        scenario = launchFragmentInContainer(themeResId = CoreR.style.Theme_Portfolio)
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
    }

    @Test
    fun when_StartOfFragment() {
        onView(withId(R.id.tvwTitle)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun when_UserInformCodeOfCountry_shouldChangeNameCountry() {
        onView(withId(R.id.tietNumCountry)).perform(typeText("55"))
        onView(withId(R.id.tvwCountry)).check(matches(ViewMatchers.withText("Brasil")))
        closeSoftKeyboard()
    }

    @Test
    fun when_UserInformCodeOfCountryField_shouldGetMaskOfCountry() {
        onView(withId(R.id.tietNumCountry)).perform(typeText("55"))
        onView(withId(R.id.tvwCountry)).check(matches(ViewMatchers.withText("Brasil")))
        onView(withId(R.id.tietNumPhone)).perform(typeText("99999999999"))
        onView(withId(R.id.tietNumPhone)).check(matches(ViewMatchers.withText("(99) 99999-9999")))
    }

    @Test
    fun when_SendPhoneToServerAndApiReturnSuccess() {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(201).setBody("{}")
        )

        onView(withId(R.id.tietNumCountry)).perform(typeText("55"))
        onView(withId(R.id.tietNumPhone)).perform(typeText("99999999999"))
        closeSoftKeyboard()

        onView(withId(R.id.btnLogin)).perform(click())
    }

    @Test
    fun when_SendPhoneToServerButApiReturnError_shouldShowMessageError() {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(403).setBody("{}")
        )

        onView(withId(R.id.tietNumCountry)).perform(typeText("55"))
        onView(withId(R.id.tietNumPhone)).perform(typeText("99999999999"))
        closeSoftKeyboard()

        onView(withId(R.id.btnLogin)).perform(click())

        val textError = mContext.getString(R.string.err_request_general)
        onView(withId(R.id.tvwMsgError)).check(matches(ViewMatchers.withText(textError)))
    }

    @After
    fun tearDown() {
        stopKoin()
        mockWebServer.shutdown()
    }

}