package br.com.raphaelmaracaipe.portfolio.utils.validations.email

import android.os.Build
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class ValidationEmailTest {

    private val validationEmail: ValidationEmail = ValidationEmailImpl()

    @Test
    fun `when email is valid should return true`() {
        val isValid = validationEmail.isValidEmail("test@test.com")
        Assert.assertTrue(isValid)
    }

    @Test
    fun `when pass email empty should return false`() {
        val isValid = validationEmail.isValidEmail("")
        Assert.assertFalse(isValid)
    }

    @Test
    fun `when email is invalid should return false`() {
        val isValid = validationEmail.isValidEmail("test@test")
        Assert.assertFalse(isValid)
    }


}