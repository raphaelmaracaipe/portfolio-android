package br.com.raphaelmaracaipe.core.extensions

import android.os.Build
import br.com.raphaelmaracaipe.core.assets.Assets
import br.com.raphaelmaracaipe.core.assets.AssetsImpl
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class BitmapExtensionsTest {

    private lateinit var assets: Assets

    @Before
    fun setUP() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        val assetsManager = context.assets
        assets = AssetsImpl(context, assetsManager)
    }

    @Test
    fun `when convert bitmap to base64 should return text`() {
        val drawableInAssets = assets.drawableImage("br.png")
        val bitmapTransformed = drawableInAssets?.toBitmap()
        val bitmapToBase64 = bitmapTransformed?.toBase64()
        assertNotEquals("", bitmapToBase64)
    }

    @Test
    fun `when convert bitmap to byte array should return array`() {
        val drawableInAssets = assets.drawableImage("br.png")
        val bitmapTransformed = drawableInAssets?.toBitmap()
        val bitmapToArray = bitmapTransformed?.toByteArray()
        assertNotEquals(0, (bitmapToArray?.size ?: 0))
    }


}