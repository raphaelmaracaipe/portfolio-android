package br.com.raphaelmaracaipe.tests.fragments

import android.os.Bundle
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

    /**
     * Launches a fragment scenario for testing.
     *
     * @param T The type of the fragment.
     * @param navigationRes The navigation resource ID.
     * @param fragmentArgs The arguments to pass to the fragment.
     * @param onFragment The callback to invoke with the fragment.
     */
    inline fun <reified T : Fragment> fragmentScenario(
        @NavigationRes navigationRes: Int,
        fragmentArgs: Bundle? = null,
        crossinline onFragment: (fragment: Fragment) -> Unit
    ) {
        launchFragmentInHiltContainer<T>(
            fragmentArgs = fragmentArgs
        ) {
            Navigation.setViewNavController(requireView(), mockNavController)
            mockNavController.setGraph(navigationRes)

            viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                viewLifecycleOwner?.let {
                    Navigation.setViewNavController(requireView(), mockNavController)
                }
            }

            onFragment.invoke(this)
        }
    }
}