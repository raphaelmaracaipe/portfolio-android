package br.com.raphaelmaracaipe.uicountries

import android.os.Build
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.testing.FragmentScenario
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.com.raphaelmaracaipe.TestApplication
import br.com.raphaelmaracaipe.uiauth.AuthFragment
import br.com.raphaelmaracaipe.uiauth.R
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M], application = TestApplication::class)
class CountriesViewFragmentTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val mockNavController = Mockito.mock(NavController::class.java)
    private val scenario = FragmentScenario.launch(CountriesViewFragment::class.java)

    @Before
    fun setUp() {
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
            mockNavController.setGraph(R.navigation.nav_uiauth)

            fragment.viewLifecycleOwnerLiveData.observeForever {
                if (it != null) {
                    Navigation.setViewNavController(fragment.requireView(), mockNavController)
                }
            }
        }
    }

    @Test
    fun `when start view`() {
        scenario.onFragment { fragment ->
            fragment.view?.let { view ->
                val container = view.findViewById<ConstraintLayout>(R.id.container)
                Assert.assertEquals(container.visibility, View.VISIBLE)
            }
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

}