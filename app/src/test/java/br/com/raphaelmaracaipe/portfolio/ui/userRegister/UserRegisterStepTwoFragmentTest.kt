package br.com.raphaelmaracaipe.portfolio.ui.userRegister

import android.os.Build
import android.widget.Button
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.com.raphaelmaracaipe.portfolio.R
import com.google.android.material.textfield.TextInputEditText
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.M])
@RunWith(RobolectricTestRunner::class)
class UserRegisterStepTwoFragmentTest {

    private val mockNavController = Mockito.mock(NavController::class.java)
    private lateinit var scenario: FragmentScenario<UserRegisterStepTwoFragment>

    @Before
    fun setUp() {
        scenario = launchFragmentInContainer(
            themeResId = R.style.Theme_Portfolio
        )
    }

    @Test
    fun `when user tap password with 6 size`() {
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
            fragment.view?.apply {
                val tietPassword = findViewById<TextInputEditText>(R.id.tietPassword)
                tietPassword.setText("abc123")
            }
        }
    }

    @Test
    fun `when user tap password only number`() {
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
            fragment.view?.apply {
                val tietPassword = findViewById<TextInputEditText>(R.id.tietPassword)
                tietPassword.setText("1234567")
            }
        }
    }

    @Test
    fun `when user tap password with lattes uppercase and lowercase`() {
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
            fragment.view?.apply {
                val tietPassword = findViewById<TextInputEditText>(R.id.tietPassword)
                tietPassword.setText("aBcDef")
            }
        }
    }

    @Test
    fun `when user tap password patters correct`() {
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
            fragment.view?.apply {
                val tietPassword = findViewById<TextInputEditText>(R.id.tietPassword)
                tietPassword.setText("aBcDef1234")

                val btnNext = findViewById<Button>(R.id.btnNext)
                btnNext.performClick()
            }
        }
    }

}