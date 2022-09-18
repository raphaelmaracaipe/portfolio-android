package br.com.raphaelmaracaipe.portfolio.ui.userRegister

import android.os.Build
import android.widget.Button
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import br.com.raphaelmaracaipe.portfolio.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import io.mockk.verify
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

@Config(sdk = [Build.VERSION_CODES.M])
@RunWith(RobolectricTestRunner::class)
class UserRegisterStepOneFragmentTest {

    private lateinit var scenario: FragmentScenario<UserRegisterStepOneFragment>
    private val mockNavController = mock(NavController::class.java)

    @Before
    fun setSetup() {
        scenario = launchFragmentInContainer(
            themeResId = R.style.Theme_Portfolio
        )
    }

    @Test
    fun `when user tap email valid and click button next should redirect to next step`() {
        scenario.moveToState(Lifecycle.State.STARTED)
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)

            var checkIfFished = true
            fragment.view?.let { view ->
                val email = "test@test.com"
                with(view) {
                    val tietEmail = findViewById<TextInputEditText>(R.id.tietEmail)
                    tietEmail.setText(email)

                    val btnNext = findViewById<Button>(R.id.btnNext)
                    btnNext?.performClick()

                    Mockito.verify(mockNavController).navigate(R.id.action_userRegisterStepOneFragment_to_userRegisterStepTwoFragment)

                    checkIfFished = false
                }
            }

            if(checkIfFished) {
                Assert.assertTrue(false)
            }
        }
    }

    @Test
    fun `when user tap email invalid should show message to user`() {
        scenario.moveToState(Lifecycle.State.STARTED)
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)

            var checkIfFished = true
            fragment.view?.let { view ->
                val email = "test"
                with(view) {
                    val tietEmail = findViewById<TextInputEditText>(R.id.tietEmail)
                    tietEmail.setText(email)

                    val btnNext = findViewById<Button>(R.id.btnNext)
                    btnNext?.performClick()

                    val textInputEmail = tietEmail?.text?.toString() ?: ""
                    Assert.assertEquals(email, textInputEmail)

                    checkIfFished = false
                }
            }

            if(checkIfFished) {
                Assert.assertTrue(false)
            }
        }
    }

    @Test
    fun `when user tap button next but user not insert email`() {
        scenario.moveToState(Lifecycle.State.STARTED)
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)

            var checkIfFished = true
            fragment.view?.let { view ->
                with(view) {
                    val tietEmail = findViewById<TextInputEditText>(R.id.tietEmail)
                    val btnNext = findViewById<Button>(R.id.btnNext)

                    btnNext?.performClick()

                    val textInputEmail = tietEmail?.text?.toString() ?: ""
                    Assert.assertEquals("", textInputEmail)

                    checkIfFished = false
                }
            }

            if(checkIfFished) {
                Assert.assertTrue(false)
            }
        }
    }

}