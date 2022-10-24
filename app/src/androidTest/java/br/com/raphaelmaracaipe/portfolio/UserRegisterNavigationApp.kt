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
import br.com.raphaelmaracaipe.portfolio.const.ConfigsToTest
import br.com.raphaelmaracaipe.portfolio.data.sp.token.TokenSP
import br.com.raphaelmaracaipe.portfolio.data.sp.token.TokenSPImpl
import br.com.raphaelmaracaipe.portfolio.models.ConsultEmailModel
import br.com.raphaelmaracaipe.portfolio.models.TokenModel
import br.com.raphaelmaracaipe.portfolio.ui.main.MainActivity
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class UserRegisterNavigationApp {

    private val mockWebServer = MockWebServer()
    private lateinit var tokenSP: TokenSP
    private lateinit var scenario: ActivityScenario<MainActivity>
    private lateinit var context: Context

    @Before
    fun setUp() {
        ConfigsToTest.urlToMock = mockWebServer.url("").toString()
        mockWebServer.start()

        context = InstrumentationRegistry.getInstrumentation().targetContext
        tokenSP = TokenSPImpl(context)

        tokenSP.clearAll()
    }

    @Test
    fun whenUserInitApp_shouldShowScreenWithOptionsToLogin() {
        scenario = launchActivity(Intent(context, MainActivity::class.java))

        onView(
            allOf(
                withId(R.id.tvwTitle), withText(context.resources.getString(R.string.acc_title_top))
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun whenUserWantTapEmailInvalid_shouldMessageEmailInvalid() {
        scenario = launchActivity(Intent(context, MainActivity::class.java))

        onView(
            allOf(
                withId(R.id.tvwTitle), withText(context.resources.getString(R.string.acc_title_top))
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

    @Test
    fun whenUserWantRegisterOnAppAndEmailAlReadyExistInDB_shouldClickButtonToRegisterAndShowMessage() {
        val json = ConsultEmailModel(true).toJSON()

        with(mockWebServer) {
            enqueue(
                MockResponse().setResponseCode(200).setBody(json)
            )
        }

        scenario = launchActivity(Intent(context, MainActivity::class.java))

        onView(
            allOf(
                withId(R.id.tvwTitle), withText(context.resources.getString(R.string.acc_title_top))
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.btnGoToRegister)
            )
        ).perform(click())

        onView(withId(R.id.tietEmail)).perform(replaceText("test@test.com"))
        onView(
            allOf(
                withId(R.id.btnNext)
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
    fun whenUserWantRegisterOnAppAndEmailNoExistInDB_shouldClickButtonToRegisterAndGoToNextToView() {
        checkAndGoToPassword()
    }

    @Test
    fun whenUserTapPasswordWithSizeSix_shouldMessage() {
        checkAndGoToPassword()
        onView(withId(R.id.tietPassword)).perform(replaceText("1"))

        onView(
            allOf(
                withId(R.id.tvwTextTitle),
                withText(context.resources.getString(R.string.reg_title_step_two))
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun whenUserTapPasswordWithLatterUppercaseAndLowerCase_shouldMessage() {
        checkAndGoToPassword()
        onView(withId(R.id.tietPassword)).perform(replaceText("AbCdEfGh"))

        onView(
            allOf(
                withId(R.id.tvwTextTitle),
                withText(context.resources.getString(R.string.reg_title_step_two))
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun whenUserTapPasswordOnlyNumber_shouldMessage() {
        checkAndGoToPassword()
        onView(withId(R.id.tietPassword)).perform(replaceText("123456"))

        onView(
            allOf(
                withId(R.id.tvwTextTitle),
                withText(context.resources.getString(R.string.reg_title_step_two))
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun whenUserTapPasswordWithPatter_shouldGoToCode() {
        gotToCode()
    }

    @Test
    fun whenUserGoToCodeButApiReturnError_shouldMessageToClient() {
        gotToCode()

        with(mockWebServer) {
            enqueue(
                MockResponse().setResponseCode(401).setBody("{}")
            )
        }

        onView(
            allOf(
                withId(R.id.tvwTextTitle),
                withText(context.resources.getString(R.string.reg_title_step_three))
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun whenUserTapEmailButIsValid_shouldMessageWhichCodeInvalid() {
        gotToCode()

        with(mockWebServer) {
            enqueue(
                MockResponse().setResponseCode(401).setBody("{}")
            )
        }

        onView(
            allOf(
                withId(R.id.tvwTextTitle),
                withText(context.resources.getString(R.string.reg_title_step_three))
            )
        ).check(matches(isDisplayed()))

        onView(withId(R.id.edtNumber1)).perform(replaceText("1"))
        onView(withId(R.id.edtNumber2)).perform(replaceText("2"))
        onView(withId(R.id.edtNumber3)).perform(replaceText("3"))
        onView(withId(R.id.edtNumber4)).perform(replaceText("4"))

        onView(
            allOf(
                withId(R.id.tvwTextTitle),
                withText(context.resources.getString(R.string.reg_title_step_three))
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun whenUserTapCodeValid_shouldRedirectToProfile() {
        gotToCode()

        with(mockWebServer) {
            enqueue(
                MockResponse().setResponseCode(200).setBody("{}")
            )
        }

        onView(
            allOf(
                withId(R.id.tvwTextTitle),
                withText(context.resources.getString(R.string.reg_title_step_three))
            )
        ).check(matches(isDisplayed()))

        with(mockWebServer) {
            val token = TokenModel("AAA", "BBB")
            enqueue(
                MockResponse().setResponseCode(200).setBody(token.toJSON())
            )
        }

        onView(withId(R.id.edtNumber1)).perform(replaceText("3"))
        onView(withId(R.id.edtNumber2)).perform(replaceText("0"))
        onView(withId(R.id.edtNumber3)).perform(replaceText("9"))
        onView(withId(R.id.edtNumber4)).perform(replaceText("7"))

        Thread.sleep(1000)

        onView(
            allOf(
                withId(R.id.textView3),
                withText("Profile")
            )
        ).check(matches(isDisplayed()))
    }

    private fun gotToCode() {
        checkAndGoToPassword()
        onView(withId(R.id.tietPassword)).perform(replaceText("Rap51234"))

        onView(
            allOf(
                withId(R.id.btnNext)
            )
        ).perform(click())

        onView(
            allOf(
                withId(R.id.tvwTextTitle),
                withText(context.resources.getString(R.string.reg_title_step_three))
            )
        ).check(matches(isDisplayed()))
    }

    private fun checkAndGoToPassword() {
        val json = ConsultEmailModel(false).toJSON()

        with(mockWebServer) {
            enqueue(
                MockResponse().setResponseCode(200).setBody(json)
            )
        }

        scenario = launchActivity(Intent(context, MainActivity::class.java))

        onView(
            allOf(
                withId(R.id.tvwTitle), withText(context.resources.getString(R.string.acc_title_top))
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.btnGoToRegister)
            )
        ).perform(click())

        onView(withId(R.id.tietEmail)).perform(replaceText("test@test.com"))
        onView(
            allOf(
                withId(R.id.btnNext)
            )
        ).perform(click())

        Thread.sleep(100)

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