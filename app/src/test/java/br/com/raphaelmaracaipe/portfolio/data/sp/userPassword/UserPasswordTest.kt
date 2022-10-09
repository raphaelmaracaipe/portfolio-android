package br.com.raphaelmaracaipe.portfolio.data.sp.userPassword

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.M])
@RunWith(RobolectricTestRunner::class)
class UserPasswordTest {

    private lateinit var userPasswordSP: UserPassword

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        userPasswordSP = UserPasswordImpl(context)
    }

    @Test
    fun `when want password should saved and get value`() {
        val text = "raphaelmaracaipe@gmail.com"
        userPasswordSP.save(text)
        val textSaved = userPasswordSP.get()
        Assert.assertEquals(text, textSaved)
    }

}