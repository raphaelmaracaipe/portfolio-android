package br.com.raphaelmaracaipe.core.extensions

import android.os.Build
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.nio.charset.Charset

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class ByteArrayExtensionsTest {

    @Test
    fun `when get text in byte array transform to base64`() {
        val textTest = "test".toByteArray(Charset.defaultCharset())
        val inBase64 = textTest.toBase64()
        assertNotEquals("test", inBase64)
    }

}