package br.com.raphaelmaracaipe.portfolio.activities

import android.os.Build
import android.view.View
import androidx.fragment.app.FragmentContainerView
import br.com.raphaelmaracaipe.core.data.ContactRepository
import br.com.raphaelmaracaipe.core.data.CountryRepository
import br.com.raphaelmaracaipe.core.data.HandShakeRepository
import br.com.raphaelmaracaipe.core.data.KeyRepository
import br.com.raphaelmaracaipe.core.data.SeedRepository
import br.com.raphaelmaracaipe.core.data.TokenRepository
import br.com.raphaelmaracaipe.core.data.UserRepository
import br.com.raphaelmaracaipe.core.data.di.RepositoryModule
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
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.M],
    application = HiltTestApplication::class
)
@UninstallModules(RepositoryModule::class)
class MainActivityTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @BindValue
    @JvmField
    var seedRepository: SeedRepository = mockk(relaxed = true)

    @BindValue
    @JvmField
    var userRepository: UserRepository = mockk(relaxed = true)

    @BindValue
    @JvmField
    var handShakeRepository: HandShakeRepository = mockk(relaxed = true)

    @BindValue
    @JvmField
    var keyRepository: KeyRepository = mockk(relaxed = true)

    @BindValue
    @JvmField
    var tokenRepository: TokenRepository = mockk(relaxed = true)

    @BindValue
    @JvmField
    var countryRepository: CountryRepository = mockk(relaxed = true)

    @BindValue
    @JvmField
    var contactRepository: ContactRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        hiltRule.inject()
        MockKAnnotations.init(this)
    }

    @Test
    fun `when init activity should visible container main`() {
        coEvery { seedRepository.cleanSeedSaved() } returns Unit

        val controller = Robolectric.buildActivity(MainActivity::class.java)
        controller.setup()

        val activity = controller.get()
        val container = activity.findViewById<FragmentContainerView>(R.id.nav_host_fragment)

        assertEquals(View.VISIBLE, container.visibility)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

}