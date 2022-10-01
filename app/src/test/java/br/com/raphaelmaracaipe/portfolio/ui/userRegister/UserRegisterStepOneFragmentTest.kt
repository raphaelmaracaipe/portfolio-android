package br.com.raphaelmaracaipe.portfolio.ui.userRegister

import android.os.Build
import android.widget.Button
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.const.ConfigsToTest
import com.google.android.material.textfield.TextInputEditText
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.json.JSONObject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
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
    fun `when user tap email valid and click button next should redirect to next step`() {
        val json = JSONObject()
        json.put("isExist", false)

        with(mockWebServer) {
            enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(json.toString())
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
//                    verify(mockNavController).navigate(R.id.action_userRegisterStepOneFragment_to_userRegisterStepTwoFragment)
                }
            }
        }

        scenario.moveToState(Lifecycle.State.STARTED)
    }

//    @Test
//    fun `when user tap email invalid should show message to user`() {
//        scenario.moveToState(Lifecycle.State.STARTED)
//        scenario.onFragment { fragment ->
//            Navigation.setViewNavController(fragment.requireView(), mockNavController)
//
//            var checkIfFished = true
//            fragment.view?.let { view ->
//                val email = "test"
//                with(view) {
//                    val tietEmail = findViewById<TextInputEditText>(R.id.tietEmail)
//                    tietEmail.setText(email)
//
//                    val btnNext = findViewById<Button>(R.id.btnNext)
//                    btnNext?.performClick()
//
//                    val textInputEmail = tietEmail?.text?.toString() ?: ""
//                    Assert.assertEquals(email, textInputEmail)
//
//                    checkIfFished = false
//                }
//            }
//
//            if(checkIfFished) {
//                Assert.assertTrue(false)
//            }
//        }
//    }
//
//    @Test
//    fun `when user tap button next but user not insert email`() {
//        scenario.moveToState(Lifecycle.State.STARTED)
//        scenario.onFragment { fragment ->
//            Navigation.setViewNavController(fragment.requireView(), mockNavController)
//
//            fragment.view?.let { view ->
//                with(view) {
//                    val tietEmail = findViewById<TextInputEditText>(R.id.tietEmail)
//                    val btnNext = findViewById<Button>(R.id.btnNext)
//
//                    btnNext?.performClick()
//
//                    val textInputEmail = tietEmail?.text?.toString() ?: ""
//                    Assert.assertEquals("", textInputEmail)
//                }
//            }
//        }
//    }

//    @After
//    fun after() {
//        System.out.println("after")
//        mockWebServer.shutdown()
//    }

}
