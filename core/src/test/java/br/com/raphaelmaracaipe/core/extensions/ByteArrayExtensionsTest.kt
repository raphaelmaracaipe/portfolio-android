package br.com.raphaelmaracaipe.core.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import br.com.raphaelmaracaipe.core.TestApplication
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, sdk = [Build.VERSION_CODES.M])
class ByteArrayExtensionsTest {

    private var bitmap: Bitmap? = null

    @Before
    fun setUp() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        getImageInAssertsTestToBitmap(context)
    }

    @Test
    fun `when convert bytearray to base64`() {
        val byteArrayToBase64 = bitmap?.toByteArray()?.toBase64()
        assertNotEquals("", byteArrayToBase64)
    }

    private fun getImageInAssertsTestToBitmap(context: Context) {
        context.assets.open("br.png").use {
            bitmap = BitmapFactory.decodeStream(it)
        }
    }

}