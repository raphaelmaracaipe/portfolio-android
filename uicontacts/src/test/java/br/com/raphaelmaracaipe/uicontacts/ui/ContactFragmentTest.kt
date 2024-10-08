package br.com.raphaelmaracaipe.uicontacts.ui

import android.os.Build
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.raphaelmaracaipe.tests.fragments.FragmentTest
import br.com.raphaelmaracaipe.uicontacts.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.assertEquals
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
class ContactFragmentTest : FragmentTest() {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
        MockKAnnotations.init(this)
    }

    @Test
    fun `when init view should show container`() {
        fragmentScenario<ContactFragment>(
            R.navigation.nav_uicontacts
        ) { fragment ->
            fragment.view?.let { view ->
                val cltContainer = view.findViewById<ConstraintLayout>(R.id.cltContainer)
                assertEquals(View.VISIBLE, cltContainer.visibility)
            }
        }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}