package br.com.raphaelmaracaipe.portfolio.ui.userRegister

import android.os.Build
import android.widget.Button
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import br.com.raphaelmaracaipe.portfolio.R
import org.junit.Before

@Config(sdk = [Build.VERSION_CODES.M])
@RunWith(RobolectricTestRunner::class)
class UserRegisterStepTwoFragmentTest{

    private lateinit var scenario: FragmentScenario<UserRegisterStepTwoFragment>
    private val mockNavController = Mockito.mock(NavController::class.java)

    @Before
    fun setSetup() {
        scenario = launchFragmentInContainer(
            themeResId = R.style.Theme_Portfolio
        )
    }

    @Test
    fun `when user tap over button`() {
        scenario.moveToState(Lifecycle.State.STARTED)
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)

            val btnNext = fragment.view?.findViewById<Button>(R.id.btnNext)
            btnNext?.performClick()

            Mockito.verify(mockNavController).navigate(R.id.action_userRegisterStepTwoFragment_to_userRegisterStepThreeFragment)
        }
    }

}