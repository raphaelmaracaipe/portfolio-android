package br.com.raphaelmaracaipe.portfolio.ui.userRegister

import android.os.Build
import android.widget.Button
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.const.ConfigsToTest
import br.com.raphaelmaracaipe.portfolio.data.api.enums.CodeError
import br.com.raphaelmaracaipe.portfolio.data.api.models.HttpError
import br.com.raphaelmaracaipe.portfolio.models.ConsultEmail
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.M])
@RunWith(RobolectricTestRunner::class)
class UserRegisterStepOneFragmentTest {

    private val mockNavController = mock(NavController::class.java)
    private val mockWebServer = MockWebServer()

    private lateinit var scenario: FragmentScenario<UserRegisterStepOneFragment>

    @Before
    fun setUp() {
        ConfigsToTest.urlToMock = mockWebServer.url("").toString()
        mockWebServer.start()

        scenario = launchFragmentInContainer(
            themeResId = R.style.Theme_Portfolio
        )
    }

    @Test
    fun `when user tap email valid but email exist should show message to user`() {
        val json = ConsultEmail(true).toJSON()

        with(mockWebServer) {
            enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(json)
            )
        }

        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)

            fragment.view?.let { view ->
                val email = "test@test.com"
                with(view) {
                    val tietEmail = findViewById<TextInputEditText>(R.id.tietEmail)
                    tietEmail.setText(email)

                    val btnNext = findViewById<Button>(R.id.btnNext)
                    btnNext?.performClick()

                    Thread.sleep(1100)
                }
            }
        }

        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun `when user tap email valid and click button next should redirect to next step`() {
        val json = ConsultEmail(false).toJSON()

        with(mockWebServer) {
            enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(json)
            )
        }

        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)

            fragment.view?.let { view ->
                val email = "test@test.com"
                with(view) {
                    val tietEmail = findViewById<TextInputEditText>(R.id.tietEmail)
                    tietEmail.setText(email)

                    val btnNext = findViewById<Button>(R.id.btnNext)
                    btnNext?.performClick()

                    Thread.sleep(1100)
                }
            }
        }

        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun `when user want register in system but return with error should message to user`() {
        val json = HttpError(message = "", code = CodeError.USER_EMAIL_INVALID.code).toJSON()
        with(mockWebServer) {
            enqueue(
                MockResponse()
                    .setResponseCode(500)
                    .setBody(json)
            )
        }

        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)

            fragment.view?.let { view ->
                val email = "test@test.com"
                with(view) {
                    val tietEmail = findViewById<TextInputEditText>(R.id.tietEmail)
                    tietEmail.setText(email)

                    val btnNext = findViewById<Button>(R.id.btnNext)
                    btnNext?.performClick()

                    Thread.sleep(1100)
                }
            }
        }

        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun `when user tap email invalid should show message erro to user`() {
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)

            fragment.view?.let { view ->
                val email = "test"
                with(view) {
                    val tietEmail = findViewById<TextInputEditText>(R.id.tietEmail)
                    tietEmail.setText(email)

                    val tfdEmail = findViewById<TextInputLayout>(R.id.tfdEmail)
                    val textError = tfdEmail.error.toString()
                    Assert.assertEquals(context.resources.getString(R.string.reg_error_field_email), textError)
                }
            }
        }

        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @After
    fun after() {
        mockWebServer.shutdown()
    }

}
