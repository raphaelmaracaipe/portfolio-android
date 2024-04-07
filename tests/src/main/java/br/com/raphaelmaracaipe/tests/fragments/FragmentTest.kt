package br.com.raphaelmaracaipe.tests.fragments

import androidx.annotation.NavigationRes
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.com.raphaelmaracaipe.tests.extensions.launchFragmentInHiltContainer
import org.mockito.Mockito.mock

@VisibleForTesting
open class FragmentTest {

    val mockNavController: NavController by lazy {
        mock(NavController::class.java)
    }

    inline fun <reified T : Fragment> fragmentScenario(
        @NavigationRes navigationRes: Int,
        crossinline onFragment: (fragment: Fragment) -> Unit
    ) {
        launchFragmentInHiltContainer<T> {
            Navigation.setViewNavController(requireView(), mockNavController)
            mockNavController.setGraph(navigationRes)

            viewLifecycleOwnerLiveData.observeForever {
                if (it != null) {
                    Navigation.setViewNavController(requireView(), mockNavController)
                }
            }

            onFragment.invoke(this)
        }
    }

}