package br.com.raphaelmaracaipe.uiauth.ui.auth

import android.content.Context
import android.os.Build
import android.widget.Button
import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.testing.FragmentScenario
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import br.com.raphaelmaracaipe.TestApplication
import br.com.raphaelmaracaipe.core.data.UserRepository
import br.com.raphaelmaracaipe.core.di.coreModule
import br.com.raphaelmaracaipe.uiauth.R
import br.com.raphaelmaracaipe.uiauth.di.AuthUiModule
import br.com.raphaelmaracaipe.uiauth.sp.AuthSP
import br.com.raphaelmaracaipe.uiauth.sp.AuthSPImpl
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.After
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M], application = TestApplication::class)
class AuthFragmentTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val app: TestApplication = ApplicationProvider.getApplicationContext()
    private val mockNavController = mock(NavController::class.java)
    private val mContext: Context = RuntimeEnvironment.getApplication().applicationContext

    @Test
    fun `when clicked on butt and redirect to view countries`() {
        app.loadModules(AuthUiModule.allModule()) {
            val scenario = fragmentScenario()
            scenario.onFragment { fragment ->
                fragment.view?.let { view ->
                    val lltCountry = view.findViewById<ConstraintLayout>(R.id.lltCountry)
                    lltCountry.performClick()
                    verify(mockNavController).navigate(AuthFragmentDirections.goToCountriesFragment())
                }
            }
        }
    }

    @Test
    fun `when digit code 55 should return country Brasil`() {
        app.loadModules(AuthUiModule.allModule()) {
            val scenario = fragmentScenario()
            scenario.onFragment { fragment ->
                fragment.view?.let { view ->
                    val tilCodePhone = view.findViewById<TextInputLayout>(R.id.tilCodePhone)
                    val tvwCountry = view.findViewById<TextView>(R.id.tvwCountry)

                    tilCodePhone.editText?.setText("55")
                    val textCountry = tvwCountry.text

                    assertEquals("Brasil", textCountry)
                }
            }
        }
    }

    @Test
    fun `when digit code country and apply format should return number formatted`() {
        app.loadModules(AuthUiModule.allModule()) {
            val scenario = fragmentScenario()
            scenario.onFragment { fragment ->
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
    }

    @Test
    fun `when click in the button but field of code country is empty should msgError to user`() {
        app.loadModules(AuthUiModule.allModule()) {
            val scenario = fragmentScenario()
            scenario.onFragment { fragment ->
                fragment.view?.let { view ->
                    val btnLogin = view.findViewById<Button>(R.id.btnLogin)
                    btnLogin.performClick()

                    val tilCodePhone = view.findViewById<TextInputLayout>(R.id.tilCodePhone)
                    val msgErrorField = tilCodePhone.error
                    assertEquals(mContext.getString(R.string.err_field_code), msgErrorField)
                }
            }
        }
    }

    @Test
    fun `when click in the button but field of number phone is empty should msgError to user`() {
        app.loadModules(AuthUiModule.allModule()) {
            val scenario = fragmentScenario()
            scenario.onFragment { fragment ->
                fragment.view?.let { view ->
                    val tietNumCountry = view.findViewById<TextInputEditText>(R.id.tietNumCountry)
                    tietNumCountry.setText("55")

                    val btnLogin = view.findViewById<Button>(R.id.btnLogin)
                    btnLogin.performClick()

                    val tilNumPhone = view.findViewById<TextInputLayout>(R.id.tilNumPhone)
                    val msgErrorField = tilNumPhone.error
                    assertEquals(mContext.getString(R.string.err_field_code), msgErrorField)
                }
            }
        }
    }

    @Test
    fun `when send code phone but api return error`() {
        val userRepository = mockk<UserRepository>()

        val test = module {
            single<AuthSP> { AuthSPImpl(androidContext()) }
        }

        val module = module {
            viewModel { AuthViewModel(androidContext(), get(), get(), userRepository) }
            viewModel { AuthSharedViewModel() }
        }

        app.loadModules(listOf(test, coreModule, module)) {
            val scenario = fragmentScenario()
            scenario.onFragment { fragment ->
                fragment.view?.let { view ->
                    val tietNumCountry = view.findViewById<TextInputEditText>(R.id.tietNumCountry)
                    tietNumCountry.setText("55")

                    val tietNumPhone = view.findViewById<TextInputEditText>(R.id.tietNumPhone)
                    tietNumPhone.setText("99999999999")

                    val btnLogin = view.findViewById<Button>(R.id.btnLogin)
                    btnLogin.performClick()

                    coEvery { userRepository.sendCode(any()) } throws Exception("a")
                    val tvwMsgError = view.findViewById<TextView>(R.id.tvwMsgError)
                    assertEquals(mContext.getString(R.string.err_request_general), tvwMsgError.text)
                }
            }
        }
    }

    private fun fragmentScenario(): FragmentScenario<AuthFragment> {
        val scenario = FragmentScenario.launch(AuthFragment::class.java)
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
            mockNavController.setGraph(R.navigation.nav_uiauth)

            fragment.viewLifecycleOwnerLiveData.observeForever {
                if (it != null) {
                    Navigation.setViewNavController(fragment.requireView(), mockNavController)
                }
            }
        }
        return scenario
    }

    @After
    fun tearDown() {
        stopKoin()
    }

}