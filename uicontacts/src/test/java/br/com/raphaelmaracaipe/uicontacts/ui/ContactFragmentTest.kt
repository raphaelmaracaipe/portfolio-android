package br.com.raphaelmaracaipe.uicontacts.ui

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.RelativeLayout
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.raphaelmaracaipe.core.data.ContactRepository
import br.com.raphaelmaracaipe.core.data.di.RepositoryModule
import br.com.raphaelmaracaipe.core.di.CoreModule
import br.com.raphaelmaracaipe.tests.fragments.FragmentTest
import br.com.raphaelmaracaipe.uicontacts.R
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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.M],
    application = HiltTestApplication::class
)
@UninstallModules(RepositoryModule::class, CoreModule::class)
class ContactFragmentTest : FragmentTest() {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @BindValue
    @JvmField
    var context: Context = RuntimeEnvironment.getApplication().applicationContext

    @BindValue
    @JvmField
    val contactRepository: ContactRepository = mockk(relaxed = true)

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
                val cltContainer = view.findViewById<RelativeLayout>(R.id.rltContainer)
                assertEquals(View.VISIBLE, cltContainer.visibility)
            }
        }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}