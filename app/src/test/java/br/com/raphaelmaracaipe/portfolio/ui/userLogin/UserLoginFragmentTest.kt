package br.com.raphaelmaracaipe.portfolio.ui.userLogin

import android.os.Build
import android.widget.Button
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.com.raphaelmaracaipe.portfolio.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.M])
@RunWith(RobolectricTestRunner::class)
class UserLoginFragmentTest {

    private lateinit var scenario: FragmentScenario<UserLoginFragment>
    private val mockNavController = mock(NavController::class.java)

    @Before
    fun setSetup() {
        scenario = launchFragmentInContainer(
            themeResId = R.style.Theme_Portfolio
        )
    }

    @Test
    fun `when user went register in the app`() {
        scenario.moveToState(Lifecycle.State.STARTED)
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)

            val btnNext = fragment.view?.findViewById<Button>(R.id.btnGoToEmailPassword)
            btnNext?.performClick()

            verify(mockNavController).navigate(R.id.action_loginFragment_to_userRegisterStepOneFragment)
        }
    }

}