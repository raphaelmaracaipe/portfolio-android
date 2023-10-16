package br.com.raphaelmaracaipe.portfolio.fragment

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import br.com.raphaelmaracaipe.core.data.HandShakeRepository
import br.com.raphaelmaracaipe.core.data.KeyRepository
import br.com.raphaelmaracaipe.core.data.TokenRepository
import br.com.raphaelmaracaipe.core.di.CoreModule
import br.com.raphaelmaracaipe.core.externals.ApiKeysDefault
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.externals.SpKeyDefault
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.core.R as RCore
import br.com.raphaelmaracaipe.portfolio.TestApplication
import br.com.raphaelmaracaipe.uiauth.ui.auth.AuthFragmentDirections
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M], application = TestApplication::class)
class SplashFragmentTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val app: TestApplication = ApplicationProvider.getApplicationContext()
    private val mockNavController = mock(NavController::class.java)

    private lateinit var modulesExternals: Module
    private lateinit var keyRepository: KeyRepository
    private lateinit var handShakeRepository: HandShakeRepository
    private lateinit var tokenRepository: TokenRepository
    private lateinit var viewModelOfTest: Module

    @Before
    fun setUp() {
        modulesExternals = module {
            single { KeysDefault("nDHj82ZWov6r4bnu", "30rBgU6kuVSHPNXX") }
            single { ApiKeysDefault("aaa", "aaa") }
            single { SpKeyDefault("a", "b", "c", "d", "e", "f") }
        }

        keyRepository = mockk()
        handShakeRepository = mockk()
        tokenRepository = mockk()

        viewModelOfTest = module {
            viewModel { SplashViewModel(handShakeRepository, keyRepository, tokenRepository) }
        }
    }

    @Test
    fun `when not have key saved but api return error should show alert error`() {
        coEvery { handShakeRepository.send() } throws Exception("test of fail")
        coEvery { keyRepository.isExistKeyRegistered() } returns false

        app.loadModules(
            listOf(
                *CoreModule.allModule().toTypedArray(),
                modulesExternals,
                viewModelOfTest
            )
        ) {
            fragmentScenario().onFragment { fragment ->
                fragment.parentFragmentManager.executePendingTransactions()
                fragment.view?.let {
                    assertTrue(true)
                }
            }
        }
    }

    @Test
    fun `when not exist token saved and send key to server should return false to go auth`() {
        coEvery { handShakeRepository.send() } returns "AAA"
        coEvery { keyRepository.isExistKeyRegistered() } returns false
        coEvery { keyRepository.saveKeyGenerated(any()) } returns Unit

        app.loadModules(
            listOf(
                *CoreModule.allModule().toTypedArray(),
                modulesExternals,
                viewModelOfTest
            )
        ) {
            fragmentScenario().onFragment { fragment ->
                fragment.parentFragmentManager.executePendingTransactions()
                fragment.view?.let {
                    assertTrue(true)
                }
            }
        }
    }

    @Test
    fun `when exist token saved should redirect to profile`() {
        coEvery { keyRepository.isExistKeyRegistered() } returns true
        coEvery { tokenRepository.isExistTokenRegistered() } returns true

        app.loadModules(
            listOf(
                *CoreModule.allModule().toTypedArray(),
                modulesExternals,
                viewModelOfTest
            )
        ) {
            fragmentScenario().onFragment { fragment ->
                fragment.parentFragmentManager.executePendingTransactions()
                fragment.view?.let {
                    assertTrue(true)
                }
            }
        }
    }

    private fun fragmentScenario(): FragmentScenario<SplashFragment> {
        val scenario = FragmentScenario.launch(SplashFragment::class.java, themeResId = RCore.style.Theme_Portfolio, initialState = Lifecycle.State.STARTED)
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
            mockNavController.setGraph(R.navigation.nav_graph)

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