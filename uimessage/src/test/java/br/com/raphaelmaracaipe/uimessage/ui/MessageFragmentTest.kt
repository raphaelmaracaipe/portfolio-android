package br.com.raphaelmaracaipe.uimessage.ui

import android.content.Context
import android.os.Build
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.work.Configuration
import androidx.work.testing.WorkManagerTestInitHelper
import br.com.raphaelmaracaipe.core.data.AuthRepository
import br.com.raphaelmaracaipe.core.data.ContactRepository
import br.com.raphaelmaracaipe.core.data.StatusRepository
import br.com.raphaelmaracaipe.core.data.db.entities.ContactEntity
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
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
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

    @BindValue
    @JvmField
    var authRepository: AuthRepository = mockk()

    @BindValue
    @JvmField
    var contactRepository: ContactRepository = mockk()

    @Before
    fun setUp() {
        hiltRule.inject()
        MockKAnnotations.init(this)

        val config = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
    }

    @Test
    fun `when init view should show container`() = runTest {
        coEvery { statusRepository.connect() } returns Unit
        coEvery { statusRepository.onIAmOnline(any()) } returns Unit

        val contact = ContactEntity()
        val bundle = bundleOf(
            "contact" to contact.toJSON()
        )

        fragmentScenario<MessageFragment>(
            fragmentArgs = bundle,
            navigationRes = R.navigation.nav_uimessage
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