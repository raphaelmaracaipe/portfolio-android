package br.com.raphaelmaracaipe.uiprofile.ui

import android.os.Build
import android.view.View
import android.widget.Button
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import br.com.raphaelmaracaipe.core.externals.ApiKeysDefault
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.externals.SpKeyDefault
import br.com.raphaelmaracaipe.uiprofile.R
import br.com.raphaelmaracaipe.uiprofile.TestApplication
import br.com.raphaelmaracaipe.uiprofile.di.ProfileUiModule
import com.google.android.material.imageview.ShapeableImageView
import org.junit.After
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M], application = TestApplication::class)
class ProfileFragmentTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val app: TestApplication = ApplicationProvider.getApplicationContext()
    private val mockNavController = Mockito.mock(NavController::class.java)
    private lateinit var modules: List<Module>

    @Before
    fun setUp() {
        val modulesToTest = module {
            single { KeysDefault("nDHj82ZWov6r4bnu", "30rBgU6kuVSHPNXX") }
            single { ApiKeysDefault("aaa", "aaa") }
            single {
                SpKeyDefault(
                    "AAA",
                    "AAA",
                    "AAA",
                    "AAA",
                    "AAA",
                    "AAA",
                    "AAA",
                    "AAA"
                )
            }
        }

        modules = listOf(
            *ProfileUiModule.allModule().toTypedArray(),
            modulesToTest
        )
    }

    @Test
    fun `when init view should show profile`() {
        app.loadModules(modules) {
            val scenario = fragmentScenario()
            scenario.onFragment { fragment ->
                fragment.view?.let { view ->
                    val sivProfile = view.findViewById<ShapeableImageView>(R.id.sivProfile)
                    assertEquals(View.VISIBLE, sivProfile.visibility)
                }
            }
        }
    }

    @Test
    fun `when click in profile`() {
        app.loadModules(modules) {
            val scenario = fragmentScenario()
            scenario.onFragment { fragment ->
                fragment.view?.let { view ->
                    val sivProfile = view.findViewById<ShapeableImageView>(R.id.sivProfile)
                    sivProfile.performClick()
                    assertTrue(true)
                }
            }
        }
    }

    @Test
    fun `when click in continue`() {
        app.loadModules(modules) {
            val scenario = fragmentScenario()
            scenario.onFragment { fragment ->
                fragment.view?.let { view ->
                    val btnContinue = view.findViewById<Button>(R.id.btnContinue)
                    btnContinue.performClick()
                    assertTrue(true)
                }
            }
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    private fun fragmentScenario(): FragmentScenario<ProfileFragment> {
        val scenario = FragmentScenario.launch(ProfileFragment::class.java)
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
            mockNavController.setGraph(R.navigation.nav_uiprofile)

            fragment.viewLifecycleOwnerLiveData.observeForever {
                if (it != null) {
                    Navigation.setViewNavController(fragment.requireView(), mockNavController)
                }
            }
        }
        return scenario
    }

}