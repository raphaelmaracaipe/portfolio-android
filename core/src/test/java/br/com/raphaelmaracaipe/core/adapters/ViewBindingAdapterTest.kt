package br.com.raphaelmaracaipe.core.adapters

import android.content.Context
import android.os.Build
import android.view.View
import br.com.raphaelmaracaipe.core.R
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class ViewBindingAdapterTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication().applicationContext
        context.setTheme(R.style.Theme_Portfolio)
    }

    @Test
    fun `when change visibility to true should show`() {
        val view = View(context)
        view.visible(true)

        assertEquals(View.VISIBLE, view.visibility)
    }

    @Test
    fun `when change visibility to false should hidden`() {
        val view = View(context)
        view.visible(false)

        assertEquals(View.GONE, view.visibility)
    }

}