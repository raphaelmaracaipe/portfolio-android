package br.com.raphaelmaracaipe.core.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import br.com.raphaelmaracaipe.core.R
import com.google.android.material.imageview.ShapeableImageView
import org.junit.Assert.*

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class ShapeableImageViewAdapterTest {

    private lateinit var context: Context

    @Test
    fun `when apply image default`() {
        try {
            context = RuntimeEnvironment.getApplication().applicationContext
            val bitmap = getBitmapFromResource()
            val shapeableImageView = ShapeableImageView(context)
            shapeableImageView.setImage(bitmap)

            assertTrue(true)
        } catch (e: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when apply image another`() {
        try {
            context = RuntimeEnvironment.getApplication().applicationContext
            val drawable = context.getDrawable(R.drawable.ic_icon)
            val shapeableImageView = ShapeableImageView(context)
            shapeableImageView.setImage(imageResourceIfNotExistBitmap = drawable)

            assertTrue(true)
        } catch (e: Exception) {
            assertTrue(false)
        }
    }

    private fun getBitmapFromResource() = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.sem_image,
        getOptions()
    )

    private fun getOptions() = BitmapFactory.Options().apply {
        inScaled = false
    }

}