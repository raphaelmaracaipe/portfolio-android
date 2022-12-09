package br.com.raphaelmaracaipe.portfolio.utils.extensions

import android.os.Build
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class StringTest {

    @Test
    fun `when want remove special character should return text careful`() {
        val text = "portfólio".removeNonSpacingMarks()
        Assert.assertEquals("portfolio", text)
    }

}