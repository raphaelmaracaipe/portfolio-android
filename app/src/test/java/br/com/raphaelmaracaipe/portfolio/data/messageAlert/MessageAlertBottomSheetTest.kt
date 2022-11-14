package br.com.raphaelmaracaipe.portfolio.data.messageAlert

import android.os.Build
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.ui.messageAlert.MessageAlertBottomSheet
import br.com.raphaelmaracaipe.portfolio.ui.userRegister.UserRegisterStepOneFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.M])
@RunWith(RobolectricTestRunner::class)
class MessageAlertBottomSheetTest {

    private lateinit var scenario: FragmentScenario<UserRegisterStepOneFragment>

    @Before
    fun setUp() {
        scenario = launchFragmentInContainer(
            themeResId = R.style.Theme_Portfolio
        )
    }

    @Test
    fun `when show alert with message should show message`() {
        scenario.onFragment {
            val fragmentManager = it.parentFragmentManager
            MessageAlertBottomSheet.showAlertMessage(
                fragmentManager,
                title = "test unit"
            )
        }
    }

}