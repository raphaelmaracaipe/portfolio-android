package br.com.raphaelmaracaipe.portfolio.ui.userRegister

import android.os.Build
import android.widget.TextView
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import br.com.raphaelmaracaipe.portfolio.R
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.M])
@RunWith(RobolectricTestRunner::class)
class UserRegisterStepThreeFragmentTest {

    private lateinit var scenario: FragmentScenario<UserRegisterStepThreeFragment>
    private val mockNavController = Mockito.mock(NavController::class.java)

    @Before
    fun setSetup() {
        scenario = launchFragmentInContainer(
            themeResId = R.style.Theme_Portfolio
        )
    }

    @Test
    fun `test if activity initializer`() {
        scenario.moveToState(Lifecycle.State.STARTED)
        scenario.onFragment { fragment ->
            val tvwTextTitle = fragment.view?.findViewById<TextView>(R.id.tvwTextTitle)
            Assert.assertNotEquals("", tvwTextTitle)
        }
    }

}