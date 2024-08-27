package br.com.raphaelmaracaipe.uiprofile.ui

import android.os.Build
import android.view.View
import android.widget.Button
import br.com.raphaelmaracaipe.core.data.UserRepository
import br.com.raphaelmaracaipe.core.data.di.RepositoryModule
import br.com.raphaelmaracaipe.tests.fragments.FragmentTest
import br.com.raphaelmaracaipe.uiprofile.R
import com.google.android.material.imageview.ShapeableImageView
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import io.mockk.MockKAnnotations
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
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
class ProfileFragmentTest : FragmentTest() {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @BindValue
    @JvmField
    var userRepository: UserRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        hiltRule.inject()
        MockKAnnotations.init(this)
    }

    @Test
    fun `when init view should show profile`() {
        fragmentScenario<ProfileFragment>(
            R.navigation.nav_uiprofile
        ) { fragment ->
            val sivProfile = fragment.view?.findViewById<ShapeableImageView>(R.id.sivProfile)
            assertEquals(View.VISIBLE, sivProfile?.visibility)
        }
    }

    @Test
    fun `when click in profile`() {
        fragmentScenario<ProfileFragment>(
            R.navigation.nav_uiprofile
        ) { fragment ->
            val sivProfile = fragment.view?.findViewById<ShapeableImageView>(R.id.sivProfile)
            sivProfile?.performClick()
            assertTrue(true)
        }
    }

    @Test
    fun `when click in continue`() {
        fragmentScenario<ProfileFragment>(
            R.navigation.nav_uiprofile
        ) { fragment ->
            fragment.view?.let { view ->
                val btnContinue = view.findViewById<Button>(R.id.btnContinue)
                btnContinue.performClick()
                assertTrue(true)
            }
        }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}