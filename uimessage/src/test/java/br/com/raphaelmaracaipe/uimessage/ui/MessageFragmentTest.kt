package br.com.raphaelmaracaipe.uimessage.ui

import android.content.Context
import android.os.Build
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.raphaelmaracaipe.core.data.StatusRepository
import br.com.raphaelmaracaipe.core.data.di.RepositoryModule
import br.com.raphaelmaracaipe.core.di.CoreModule
import br.com.raphaelmaracaipe.tests.fragments.FragmentTest
import br.com.raphaelmaracaipe.uimessage.R
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import io.mockk.MockKAnnotations
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import org.junit.After
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
class MessageFragmentTest : FragmentTest() {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @BindValue
    @JvmField
    var context: Context = RuntimeEnvironment.getApplication().applicationContext

    @BindValue
    @JvmField
    var statusRepository: StatusRepository = mockk()

    @Before
    fun setUp() {
        hiltRule.inject()
        MockKAnnotations.init(this)
    }

    @Test
    fun `when init view should show container`() {
        fragmentScenario<MessageFragment>(
            R.navigation.nav_uimessage
        ) { fragment ->
            fragment.view?.let { view ->
                val cltContainer = view.findViewById<ConstraintLayout>(R.id.containerMain)
                assertEquals(View.VISIBLE, cltContainer.visibility)
            }
        }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

}