package br.com.raphaelmaracaipe.uiauth.ui.countries

import android.os.Build
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.testing.FragmentScenario
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import br.com.raphaelmaracaipe.TestApplication
import br.com.raphaelmaracaipe.uiauth.R
import br.com.raphaelmaracaipe.uiauth.di.AuthUiModule
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.shadow.api.Shadow
import org.robolectric.shadows.ShadowLooper

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M], application = TestApplication::class)
class CountriesViewFragmentTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val app: TestApplication = ApplicationProvider.getApplicationContext()
    private val mockNavController: NavController = Mockito.mock(NavController::class.java)

    @Test
    fun `when start view`() {
        app.loadModules(AuthUiModule.allModule()) {
            fragmentScenario().onFragment { fragment ->
                fragment.view?.let { view ->
                    val container = view.findViewById<CoordinatorLayout>(R.id.container)
                    Assert.assertEquals(container.visibility, View.VISIBLE)
                }
            }
        }
    }

    @Test
    fun `when start view is request list of countries`() = runBlocking {
        app.loadModules(AuthUiModule.allModule()) {
            fragmentScenario().onFragment { fragment ->
                fragment.view?.let { view ->
                    Thread.sleep(1000)

                    val rvwItemSearch = view.findViewById<RecyclerView>(R.id.rvwItemSearch)
                    val sizeListOfCountries = rvwItemSearch.adapter?.itemCount ?: 0
                    Assert.assertNotEquals(0, sizeListOfCountries)
                }
            }
        }
    }

    @Test
    fun `when click in item of recyclerview`() = runBlocking {
        app.loadModules(AuthUiModule.allModule()) {
            fragmentScenario().onFragment { fragment ->
                fragment.view?.let { view ->
                    Thread.sleep(1000)

                    val rvwItemSearch = view.findViewById<RecyclerView>(R.id.rvwItemSearch)
                    rvwItemSearch.measure(
                        View.MeasureSpec.UNSPECIFIED,
                        View.MeasureSpec.UNSPECIFIED
                    )
                    rvwItemSearch.layout(0, 0, 1000, 1000)
                    rvwItemSearch.findViewHolderForAdapterPosition(0)?.itemView?.performClick()

                    verify(mockNavController).navigateUp()
                }
            }
        }
    }

    private fun fragmentScenario(): FragmentScenario<CountriesFragment> {
        val scenario = FragmentScenario.launch(CountriesFragment::class.java)
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
            mockNavController.setGraph(R.navigation.nav_uiauth)

            fragment.viewLifecycleOwnerLiveData.observeForever {
                if (it != null) {
                    Navigation.setViewNavController(fragment.requireView(), mockNavController)
                }
            }
        }
        return scenario
    }

    @After
    fun tearDown() {
        stopKoin()
    }

}