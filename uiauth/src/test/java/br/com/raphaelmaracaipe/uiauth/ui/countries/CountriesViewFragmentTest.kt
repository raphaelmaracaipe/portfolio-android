package br.com.raphaelmaracaipe.uiauth.ui.countries

import android.os.Build
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import br.com.raphaelmaracaipe.tests.fragments.FragmentTest
import br.com.raphaelmaracaipe.uiauth.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.M],
    application = HiltTestApplication::class
)
class CountriesViewFragmentTest : FragmentTest() {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
        MockKAnnotations.init(this)
    }

    @Test
    fun `when start view`() {
        fragmentScenario<CountriesFragment>(
            R.navigation.nav_uiauth
        ) { fragment ->
            fragment.view?.let { view ->
                val container = view.findViewById<CoordinatorLayout>(R.id.container)
                Assert.assertEquals(container.visibility, View.VISIBLE)
            }
        }
    }

    @Test
    fun `when start view is request list of countries`() = runBlocking {
        fragmentScenario<CountriesFragment>(
            R.navigation.nav_uiauth
        ) { fragment ->
            fragment.view?.let { view ->
                Thread.sleep(1000)

                val rvwItemSearch = view.findViewById<RecyclerView>(R.id.rvwItemSearch)
                val sizeListOfCountries = rvwItemSearch.adapter?.itemCount ?: 0
                Assert.assertNotEquals(0, sizeListOfCountries)
            }
        }
    }

    @Test
    fun `when click in item of recyclerview`() = runBlocking {
        fragmentScenario<CountriesFragment>(
            R.navigation.nav_uiauth
        ) { fragment ->
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

    @After
    fun tearDown() {
        unmockkAll()
    }

}