package br.com.raphaelmaracaipe.core.navigation

import android.os.Build
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class NavigationURITest {

    @Test
    fun `when check if exist data in profile`() {
        val profile = NavigationURI.PROFILE
        assertNotEquals("", profile.toString())
    }

    @Test
    fun `when check if exist data in contact`() {
        val contact = NavigationURI.CONTACTS
        assertNotEquals("", contact.toString())
    }

}