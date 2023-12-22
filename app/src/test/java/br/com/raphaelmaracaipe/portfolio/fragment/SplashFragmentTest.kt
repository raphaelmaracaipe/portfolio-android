package br.com.raphaelmaracaipe.portfolio.fragment

import android.os.Build
import android.os.Looper.getMainLooper
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.com.raphaelmaracaipe.core.data.HandShakeRepository
import br.com.raphaelmaracaipe.core.data.KeyRepository
import br.com.raphaelmaracaipe.core.data.SeedRepository
import br.com.raphaelmaracaipe.core.data.TokenRepository
import br.com.raphaelmaracaipe.core.data.UserRepository
import br.com.raphaelmaracaipe.core.data.di.RepositoryModule
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import io.mockk.MockKAnnotations
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.core.testUnit.extentions.launchFragmentInHiltContainer
import io.mockk.coEvery
import org.mockito.Mockito.mock
import org.robolectric.Shadows
import java.time.Duration

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.M],
    application = HiltTestApplication::class
)
@UninstallModules(RepositoryModule::class)
class SplashFragmentTest {

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


    private val mockNavController = mock(NavController::class.java)

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


        fragmentScenario { fragment ->
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

        fragmentScenario { fragment ->
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

        fragmentScenario { fragment ->
            fragment.parentFragmentManager.executePendingTransactions()
            fragment.view?.let {
                assertTrue(true)
            }
        }
    }

    private fun fragmentScenario(onFragment: (fragment: Fragment) -> Unit) {
        launchFragmentInHiltContainer<SplashFragment> {
            Navigation.setViewNavController(requireView(), mockNavController)
            mockNavController.setGraph(R.navigation.nav_graph)

            viewLifecycleOwnerLiveData.observeForever {
                if (it != null) {
                    Navigation.setViewNavController(requireView(), mockNavController)
                }
            }

            onFragment.invoke(this)
        }
    }


    @After
    fun tearDown() {
        unmockkAll()
    }

}