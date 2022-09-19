package br.com.raphaelmaracaipe.portfolio

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import br.com.raphaelmaracaipe.portfolio.ui.main.MainActivity
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class UserLoginNavigationApp {

    private lateinit var scenario: ActivityScenario<MainActivity>
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun whenUserInitApp_shouldShowScreenWithOptionsToLogin() {
        scenario = launchActivity(Intent(context, MainActivity::class.java))

        onView(
            allOf(
                withId(R.id.tvwTitle),
                withText(context.resources.getString(R.string.acc_title_top))
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun whenUserWantRegisterOnApp_shouldClickButtonToRegister() {
        scenario = launchActivity(Intent(context, MainActivity::class.java))

        onView(
            allOf(
                withId(R.id.tvwTitle),
                withText(context.resources.getString(R.string.acc_title_top))
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.btnGoToRegister)
            )
        ).perform(click())

        onView(
            allOf(
                withId(R.id.tvwTextTitle),
                withText(context.resources.getString(R.string.reg_title_step_one))
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun whenUserWantTapEmailInvalid_shouldMessageEmailInvalid() {
        scenario = launchActivity(Intent(context, MainActivity::class.java))

        onView(
            allOf(
                withId(R.id.tvwTitle),
                withText(context.resources.getString(R.string.acc_title_top))
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.btnGoToRegister)
            )
        ).perform(click())

        onView(
            allOf(
                withId(R.id.tvwTextTitle),
                withText(context.resources.getString(R.string.reg_title_step_one))
            )
        ).check(matches(isDisplayed()))

        onView(withId(R.id.tietEmail)).perform(replaceText("test"))
        onView(
            allOf(
                withId(R.id.btnNext)
            )
        ).perform(click())

        onView(
            allOf(
                withId(R.id.tvwTextText),
                withText(context.resources.getString(R.string.reg_error_field_email))
            )
        ).check(matches(isDisplayed()))
    }

}