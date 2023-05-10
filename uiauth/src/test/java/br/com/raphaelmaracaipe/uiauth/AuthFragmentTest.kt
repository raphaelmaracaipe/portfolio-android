package br.com.raphaelmaracaipe.uiauth

import android.os.Build
import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.testing.FragmentScenario
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.com.raphaelmaracaipe.TestApplication
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import br.com.raphaelmaracaipe.uiauth.R as UiAuthR

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M], application = TestApplication::class)
class AuthFragmentTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val mockNavController = mock(NavController::class.java)
    private val scenario = FragmentScenario.launch(AuthFragment::class.java)

    @Before
    fun setUp() {
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
            mockNavController.setGraph(UiAuthR.navigation.nav_uiauth)

            fragment.viewLifecycleOwnerLiveData.observeForever {
                if (it != null) {
                    Navigation.setViewNavController(fragment.requireView(), mockNavController)
                }
            }
        }
    }

    @Test
    fun `when clicked on butt and redirect to view countries`() {
        scenario.onFragment { fragment ->
            fragment.view?.let { view ->
                val lltCountry = view.findViewById<ConstraintLayout>(UiAuthR.id.lltCountry)
                lltCountry.performClick()
                verify(mockNavController).navigate(AuthFragmentDirections.goToCountriesFragment())
            }
        }
    }

    @Test
    fun `when digit code 55 should return country Brasil`() {
        scenario.onFragment { fragment ->
            fragment.view?.let { view ->
                val tilCodePhone = view.findViewById<TextInputLayout>(UiAuthR.id.tilCodePhone)
                val tvwCountry = view.findViewById<TextView>(UiAuthR.id.tvwCountry)

                tilCodePhone.editText?.setText("55")
                val textCountry = tvwCountry.text

                assertEquals("Brasil", textCountry)
            }
        }
    }

    @Test
    fun `when digit code country and apply format should return number formatted`() {
        scenario.onFragment { fragment ->
            fragment.view?.let { view ->
                val tilCodePhone = view.findViewById<TextInputLayout>(UiAuthR.id.tilCodePhone)
                val tietNumPhone = view.findViewById<TextInputEditText>(UiAuthR.id.tietNumPhone)

                tilCodePhone.editText?.setText("55")
                tietNumPhone.setText("99999999999")

                val valueOfNumberPhone = tietNumPhone.text.toString()
                assertEquals("(99) 99999-9999", valueOfNumberPhone)
            }
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

}