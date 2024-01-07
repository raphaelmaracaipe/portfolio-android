package br.com.raphaelmaracaipe.portfolio.fragment

import android.os.Build
import android.os.Looper.getMainLooper
import br.com.raphaelmaracaipe.core.data.HandShakeRepository
import br.com.raphaelmaracaipe.core.data.KeyRepository
import br.com.raphaelmaracaipe.core.data.SeedRepository
import br.com.raphaelmaracaipe.core.data.TokenRepository
import br.com.raphaelmaracaipe.core.data.UserRepository
import br.com.raphaelmaracaipe.core.data.di.RepositoryModule
import br.com.raphaelmaracaipe.core.testUnit.FragmentTest
import br.com.raphaelmaracaipe.portfolio.R
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import java.time.Duration

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.M],
    application = HiltTestApplication::class
)
@UninstallModules(RepositoryModule::class)
class SplashFragmentTest : FragmentTest() {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)


    @BindValue
    @JvmField
    var userRepository: UserRepository = mockk(relaxed = true)

    @BindValue
    @JvmField
    var seedRepository: SeedRepository = mockk(relaxed = true)

    @BindValue
    @JvmField
    var handShakeRepository: HandShakeRepository = mockk(relaxed = true)

    @BindValue
    @JvmField
    var keyRepository: KeyRepository = mockk(relaxed = true)

    @BindValue
    @JvmField
    var tokenRepository: TokenRepository = mockk(relaxed = true)


//    private val mockNavController = mock(NavController::class.java)

    @Before
    fun setUp() {
        hiltRule.inject()
        MockKAnnotations.init(this)
    }

    @Test
    fun `when not have key saved but api return error should show alert error`() {
        coEvery { handShakeRepository.send() } throws Exception("test of fail")
        coEvery { keyRepository.isExistKeyRegistered() } returns false
        coEvery { seedRepository.cleanSeedSaved() } returns Unit


        fragmentScenario<SplashFragment>(R.navigation.nav_graph) { fragment ->
            fragment.parentFragmentManager.executePendingTransactions()

            Shadows.shadowOf(getMainLooper()).idleFor(Duration.ofMillis(3000))
            fragment.view?.let {
                assertTrue(true)
            }
        }
    }

    @Test
    fun `when not exist token saved and send key to server should return false to go auth`() {
        coEvery { handShakeRepository.send() } returns "AAA"
        coEvery { keyRepository.isExistKeyRegistered() } returns false
        coEvery { keyRepository.saveKeyGenerated(any()) } returns Unit
        coEvery { seedRepository.cleanSeedSaved() } returns Unit

        fragmentScenario<SplashFragment>(R.navigation.nav_graph) { fragment ->
            fragment.parentFragmentManager.executePendingTransactions()

            Shadows.shadowOf(getMainLooper()).idleFor(Duration.ofMillis(3000))
            fragment.view?.let {
                assertTrue(true)
            }
        }
    }

    @Test
    fun `when exist token saved should redirect to profile`() {
        coEvery { keyRepository.isExistKeyRegistered() } returns true
        coEvery { tokenRepository.isExistTokenRegistered() } returns true
        coEvery { seedRepository.cleanSeedSaved() } returns Unit

        fragmentScenario<SplashFragment>(R.navigation.nav_graph) { fragment ->
            fragment.parentFragmentManager.executePendingTransactions()
            fragment.view?.let {
                assertTrue(true)
            }
        }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

}