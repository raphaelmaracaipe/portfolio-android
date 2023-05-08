package br.com.raphaelmaracaipe.uiauth

import android.os.Build
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.testing.FragmentScenario
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import br.com.raphaelmaracaipe.TestApplication
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M], application = TestApplication::class)
class AuthFragmentTest {

    @Test
    fun a() {
        val mockNavController = mock(NavController::class.java)

        val scenario = FragmentScenario.launch(AuthFragment::class.java)
        scenario.onFragment { fragment ->
            fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                if(viewLifecycleOwner != null) {
                    Navigation.setViewNavController(fragment.requireView(), mockNavController)
                }
            }

            fragment.view?.let { view ->
                val lltCountry = view.findViewById<ConstraintLayout>(R.id.lltCountry)
                lltCountry.performClick()

                verify(mockNavController).navigate(R.id.goToCountriesFragment)
            }
        }
    }

}