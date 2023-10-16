package br.com.raphaelmaracaipe.portfolio.activities

import android.os.Build
import android.view.View
import androidx.fragment.app.FragmentContainerView
import androidx.test.core.app.ApplicationProvider
import br.com.raphaelmaracaipe.core.data.HandShakeRepository
import br.com.raphaelmaracaipe.core.data.KeyRepository
import br.com.raphaelmaracaipe.core.data.SeedRepository
import br.com.raphaelmaracaipe.core.data.TokenRepository
import br.com.raphaelmaracaipe.core.di.CoreModule
import br.com.raphaelmaracaipe.core.externals.ApiKeysDefault
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.externals.SpKeyDefault
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.TestApplication
import br.com.raphaelmaracaipe.portfolio.fragment.SplashViewModel
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M], application = TestApplication::class)
class MainActivityTest {

    private val app: TestApplication = ApplicationProvider.getApplicationContext()

    private lateinit var modulesExternals: Module
    private lateinit var keyRepository: KeyRepository
    private lateinit var handShakeRepository: HandShakeRepository
    private lateinit var seedRepository: SeedRepository
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
        seedRepository = mockk()

        viewModelOfTest = module {
            viewModel { SplashViewModel(handShakeRepository, keyRepository, tokenRepository, seedRepository) }
        }
    }

    @Test
    fun `when init activity should visible container main`() {
        coEvery { seedRepository.cleanSeedSaved() } returns Unit

        app.loadModules(listOf(
            *CoreModule.allModule().toTypedArray(),
            modulesExternals,
            viewModelOfTest
        )) {
            val controller = Robolectric.buildActivity(MainActivity::class.java)
            controller.setup()

            val activity = controller.get()
            val container = activity.findViewById<FragmentContainerView>(R.id.nav_host_fragment)

            assertEquals(View.VISIBLE, container.visibility)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

}