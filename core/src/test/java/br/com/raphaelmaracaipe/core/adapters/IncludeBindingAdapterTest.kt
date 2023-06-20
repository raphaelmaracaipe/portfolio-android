package br.com.raphaelmaracaipe.core.adapters

import android.content.Context
import android.os.Build
import android.view.View
import br.com.raphaelmaracaipe.core.R
import br.com.raphaelmaracaipe.core.TestApplication
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class IncludeBindingAdapterTest {

    private lateinit var mContext: Context

    @Before
    fun setUp() {
        mContext = RuntimeEnvironment.getApplication().applicationContext
        mContext.setTheme(R.style.Theme_Portfolio)
    }

    @Test
    fun `when set visible like true should put gone`() {
        val view = View(mContext)
        view.visibility = View.GONE
        view.visible(true)
        Assert.assertEquals(View.VISIBLE, view.visibility)
    }

    @Test
    fun `when set visible like false should put visible`() {
        val view = View(mContext)
        view.visibility = View.VISIBLE
        view.visible(false)
        Assert.assertEquals(View.GONE, view.visibility)
    }

}