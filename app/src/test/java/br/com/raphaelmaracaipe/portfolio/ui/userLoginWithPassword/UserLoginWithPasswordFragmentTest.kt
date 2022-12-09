package br.com.raphaelmaracaipe.portfolio.ui.userLoginWithPassword

import android.os.Build
import android.widget.Button
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
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
class UserLoginWithPasswordFragmentTest {

    private lateinit var scenario: FragmentScenario<UserLoginWithPasswordFragment>
    private val mockNavController = Mockito.mock(NavController::class.java)

    @Before
    fun setSetup() {
        scenario = launchFragmentInContainer(
            themeResId = R.style.Theme_Portfolio
        )
    }

    @Test
    fun `when login in api`(){
        scenario.moveToState(Lifecycle.State.STARTED)
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)

            fragment.view?.apply {
                val tietPassword = findViewById<TextInputEditText>(R.id.tietPassword)
                tietPassword.setText("1234567")

                val btnAccess = fragment.view?.findViewById<Button>(R.id.btnAccess)
                btnAccess?.performClick()
            }
        }
    }

}