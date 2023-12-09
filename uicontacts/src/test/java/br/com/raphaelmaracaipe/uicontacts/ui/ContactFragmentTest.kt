package br.com.raphaelmaracaipe.uicontacts.ui

import android.os.Build
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.testing.FragmentScenario
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.com.raphaelmaracaipe.uicontacts.R
import br.com.raphaelmaracaipe.uicontacts.TestApplication
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M], application = TestApplication::class)
class ContactFragmentTest {

    private val mockNavController = Mockito.mock(NavController::class.java)

    @Test
    fun `when init view should show container`() {
        fragmentScenario().onFragment { fragment ->
            fragment.view?.let { view ->
                val cltContainer = view.findViewById<ConstraintLayout>(R.id.cltContainer)
                assertEquals(View.VISIBLE, cltContainer.visibility)
            }
        }
    }

    private fun fragmentScenario(): FragmentScenario<ContactFragment> {
        val scenario = FragmentScenario.launch(ContactFragment::class.java)
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
            mockNavController.setGraph(R.navigation.nav_uicontacts)

            fragment.viewLifecycleOwnerLiveData.observeForever {
                if (it != null) {
                    Navigation.setViewNavController(fragment.requireView(), mockNavController)
                }
            }
        }
        return scenario
    }
}