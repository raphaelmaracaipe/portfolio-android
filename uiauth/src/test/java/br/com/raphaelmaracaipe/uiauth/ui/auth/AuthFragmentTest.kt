package br.com.raphaelmaracaipe.uiauth.ui.auth

import android.content.Context
import android.os.Build
import android.widget.Button
import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.raphaelmaracaipe.core.assets.Assets
import br.com.raphaelmaracaipe.core.data.UserRepository
import br.com.raphaelmaracaipe.core.data.di.RepositoryModule
import br.com.raphaelmaracaipe.core.di.CoreModule
import br.com.raphaelmaracaipe.core.testUnit.FragmentTest
import br.com.raphaelmaracaipe.uiauth.R
import br.com.raphaelmaracaipe.uiauth.models.CodeCountry
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
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
class AuthFragmentTest : FragmentTest() {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @BindValue
    @JvmField
    var userRepository: UserRepository = mockk(relaxed = true)

    @BindValue
    @JvmField
    var assets: Assets = mockk(relaxed = true)

    @BindValue
    @JvmField
    var context: Context = RuntimeEnvironment.getApplication().applicationContext

    @Before
    fun setUp() {
        hiltRule.inject()
        MockKAnnotations.init(this)
    }

    @Test
    fun `when clicked on butt and redirect to view countries`() {
        fragmentScenario<AuthFragment>(R.navigation.nav_uiauth) { fragment ->
            fragment.view?.let { view ->
                val lltCountry = view.findViewById<ConstraintLayout>(R.id.lltCountry)
                lltCountry.performClick()
                verify(mockNavController).navigate(AuthFragmentDirections.goToCountriesFragment())
            }
        }
    }

    @Test
    fun `when digit code 55 should return country Brasil`() {
        val arrayCountries = arrayOf(
            CodeCountry("Brasil", "55", "BR / BRA")
        )
        val json = Gson().toJson(arrayCountries)
        every { assets.read(any()) }.returns(json)

        fragmentScenario<AuthFragment>(R.navigation.nav_uiauth) { fragment ->
            fragment.view?.let { view ->
                val tilCodePhone = view.findViewById<TextInputLayout>(R.id.tilCodePhone)
                val tvwCountry = view.findViewById<TextView>(R.id.tvwCountry)

                tilCodePhone.editText?.setText("55")
                val textCountry = tvwCountry.text

                assertEquals("Brasil", textCountry)
            }
        }
    }

    @Test
    fun `when digit code country and apply format should return number formatted`() {
        val arrayCountries = arrayOf(
            CodeCountry("Brasil", "55", "BR / BRA")
        )
        val json = Gson().toJson(arrayCountries)
        every { assets.read(any()) }.returns(json)

        fragmentScenario<AuthFragment>(R.navigation.nav_uiauth) { fragment ->
            fragment.view?.let { view ->
                val tilCodePhone = view.findViewById<TextInputLayout>(R.id.tilCodePhone)
                val tietNumPhone = view.findViewById<TextInputEditText>(R.id.tietNumPhone)

                tilCodePhone.editText?.setText("55")
                tietNumPhone.setText("99999999999")

                val valueOfNumberPhone = tietNumPhone.text.toString()
                assertEquals("(99) 99999-9999", valueOfNumberPhone)
            }
        }
    }

    @Test
    fun `when click in the button but field of code country is empty should msgError to user`() {
        fragmentScenario<AuthFragment>(R.navigation.nav_uiauth) { fragment ->
            fragment.view?.let { view ->
                val btnLogin = view.findViewById<Button>(R.id.btnLogin)
                btnLogin.performClick()

                val tilCodePhone = view.findViewById<TextInputLayout>(R.id.tilCodePhone)
                val msgErrorField = tilCodePhone.error
                assertEquals(context.getString(R.string.err_field_code), msgErrorField)
            }
        }
    }

    @Test
    fun `when click in the button but field of number phone is empty should msgError to user`() {
        val arrayCountries = arrayOf(
            CodeCountry("Brasil", "55", "BR / BRA")
        )
        val json = Gson().toJson(arrayCountries)
        every { assets.read(any()) }.returns(json)

        fragmentScenario<AuthFragment>(R.navigation.nav_uiauth) { fragment ->
            fragment.view?.let { view ->
                val tietNumCountry = view.findViewById<TextInputEditText>(R.id.tietNumCountry)
                tietNumCountry.setText("55")

                val btnLogin = view.findViewById<Button>(R.id.btnLogin)
                btnLogin.performClick()

                val tilNumPhone = view.findViewById<TextInputLayout>(R.id.tilNumPhone)
                val msgErrorField = tilNumPhone.error
                assertEquals(context.getString(R.string.err_field_code), msgErrorField)
            }
        }
    }

    @Test
    fun `when send code phone but api return error`() {
        val arrayCountries = arrayOf(
            CodeCountry("Brasil", "55", "BR / BRA")
        )
        val json = Gson().toJson(arrayCountries)

        every { assets.read(any()) }.returns(json)
        coEvery { userRepository.sendCode(any()) } throws Exception("a")

        fragmentScenario<AuthFragment>(R.navigation.nav_uiauth) { fragment ->
            fragment.view?.let { view ->
                val tietNumCountry = view.findViewById<TextInputEditText>(R.id.tietNumCountry)
                tietNumCountry.setText("55")

                val tietNumPhone = view.findViewById<TextInputEditText>(R.id.tietNumPhone)
                tietNumPhone.setText("99999999999")

                val btnLogin = view.findViewById<Button>(R.id.btnLogin)
                btnLogin.performClick()

                val tvwMsgError = view.findViewById<TextView>(R.id.tvwMsgError)
                assertEquals(context.getString(R.string.err_request_general), tvwMsgError.text)
            }
        }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

}