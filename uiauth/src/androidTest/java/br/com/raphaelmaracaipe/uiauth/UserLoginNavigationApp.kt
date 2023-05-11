package br.com.raphaelmaracaipe.uiauth

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import br.com.raphaelmaracaipe.uiauth.di.AuthUiModule
import br.com.raphaelmaracaipe.uiauth.ui.auth.AuthFragment
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import br.com.raphaelmaracaipe.core.R as CoreR

@RunWith(AndroidJUnit4::class)
class UserLoginNavigationApp {

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        startKoin {
            androidLogger()
            androidContext(context)
            loadKoinModules(AuthUiModule.allModule())
        }


        launchFragmentInContainer<AuthFragment>(themeResId = CoreR.style.Theme_Portfolio)
    }

    @Test
    fun when_StartOfFragment() {
        onView(withId(R.id.tvwTitle)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun when_UserInformCodeOfCountry_shouldChangeNameCountry() {
        onView(withId(R.id.tietNumCountry)).perform(replaceText("55"))
        onView(withId(R.id.tvwCountry)).check(matches(ViewMatchers.withText("Brasil")))
    }

    @Test
    fun when_UserInformCodeOfCountryField_shouldGetMaskOfCountry() {
        onView(withId(R.id.tietNumCountry)).perform(replaceText("55"))
        onView(withId(R.id.tvwCountry)).check(matches(ViewMatchers.withText("Brasil")))
        onView(withId(R.id.tietNumPhone)).perform(replaceText("99999999999"))
        onView(withId(R.id.tietNumPhone)).check(matches(ViewMatchers.withText("(99) 99999-9999")))
    }

    @After
    fun tearDown() {
        stopKoin()
    }

}