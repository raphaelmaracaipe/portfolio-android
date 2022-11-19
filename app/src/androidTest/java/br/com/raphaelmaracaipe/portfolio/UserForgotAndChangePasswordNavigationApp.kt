package br.com.raphaelmaracaipe.portfolio

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import br.com.raphaelmaracaipe.portfolio.const.ConfigsToTest
import br.com.raphaelmaracaipe.portfolio.data.api.enums.CodeError
import br.com.raphaelmaracaipe.portfolio.data.api.enums.CodeError.*
import br.com.raphaelmaracaipe.portfolio.data.api.models.HttpError
import br.com.raphaelmaracaipe.portfolio.data.sp.token.TokenSP
import br.com.raphaelmaracaipe.portfolio.data.sp.token.TokenSPImpl
import br.com.raphaelmaracaipe.portfolio.models.ConsultEmailModel
import br.com.raphaelmaracaipe.portfolio.ui.main.MainActivity
import br.com.raphaelmaracaipe.portfolio.utils.security.encryptDecrypt.EncryptDecryptImpl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matchers
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class UserForgotAndChangePasswordNavigationApp {

    private val mockWebServer = MockWebServer()
    private lateinit var tokenSP: TokenSP
    private lateinit var scenario: ActivityScenario<MainActivity>
    private lateinit var context: Context

    @Before
    fun setUp() {
        ConfigsToTest.urlToMock = mockWebServer.url("").toString()
        mockWebServer.start()

        context = InstrumentationRegistry.getInstrumentation().targetContext
        tokenSP = TokenSPImpl(context, EncryptDecryptImpl())
    }

    @Test
    fun whenUseWantForgotPasswordButClickButtonNo() {
        gotToPassword()

        onView(
            allOf(
                withId(R.id.tvwForgotPassword)
            )
        ).perform(click())

        onView(
            allOf(
                withId(R.id.tvwTextText),
                withText(context.resources.getString(R.string.log_forgot))
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.btnCancel)
            )
        ).perform(click())
    }

    @Test
    fun whenUseWantForgotPasswordButClickButtonYes_ButApiReturnError() {
        gotToPassword()

        onView(
            allOf(
                withId(R.id.tvwForgotPassword)
            )
        ).perform(click())

        onView(
            allOf(
                withId(R.id.tvwTextText),
                withText(context.resources.getString(R.string.log_forgot))
            )
        ).check(matches(isDisplayed()))

        val json = HttpError(code = GENERAL_ERROR.code).toJSON()
        with(mockWebServer) {
            enqueue(
                MockResponse().setResponseCode(401).setBody(json)
            )
        }

        onView(
            allOf(
                withId(R.id.btnSuccess)
            )
        ).perform(click())
    }

    @Test
    fun whenUseWantForgotPasswordButClickButtonYesApiReturnSuccess() {
        gotToPassword()

        onView(
            allOf(
                withId(R.id.tvwForgotPassword)
            )
        ).perform(click())

        onView(
            allOf(
                withId(R.id.tvwTextText),
                withText(context.resources.getString(R.string.log_forgot))
            )
        ).check(matches(isDisplayed()))

        with(mockWebServer) {
            enqueue(
                MockResponse().setResponseCode(201).setBody("{}")
            )
        }

        onView(
            allOf(
                withId(R.id.btnSuccess)
            )
        ).perform(click())
    }

    private fun gotToPassword() {
        val json = ConsultEmailModel(true).toJSON()
        with(mockWebServer) {
            enqueue(
                MockResponse().setResponseCode(200).setBody(json)
            )
        }

        scenario = launchActivity(Intent(context, MainActivity::class.java))

        onView(
            allOf(
                withId(R.id.tvwTitle),
                withText(context.resources.getString(R.string.acc_title_top))
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.btnGoToEmailPassword)
            )
        ).perform(click())

        onView(
            allOf(
                withId(R.id.tvwTextTitle),
                withText(context.resources.getString(R.string.reg_title_step_one))
            )
        ).check(matches(isDisplayed()))

        onView(withId(R.id.tietEmail)).perform(replaceText("test@test.com"))
        onView(
            allOf(
                withId(R.id.btnNext)
            )
        ).perform(click())

        onView(
            allOf(
                withId(R.id.tvwTextTitle),
                withText(context.resources.getString(R.string.reg_title_step_two))
            )
        ).check(matches(isDisplayed()))
    }

    @After
    fun after() {
        mockWebServer.shutdown()
        tokenSP.clearAll()
    }

}