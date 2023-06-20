package br.com.raphaelmaracaipe.core.adapters

import android.content.Context
import android.os.Build
import android.view.View
import br.com.raphaelmaracaipe.core.R
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class ViewBindingAdapterKtTest {

    private lateinit var mContext: Context

    @Before
    fun setUp() {
        mContext = RuntimeEnvironment.getApplication().applicationContext
        mContext.setTheme(R.style.Theme_Portfolio)
    }

    @Test
    fun `when set true should visible view`() {
        val view = View(mContext)
        view.visible(true)
        Assert.assertEquals(View.VISIBLE, view.visibility)
    }

    @Test
    fun `when set false should gone view`() {
        val view = View(mContext)
        view.visible(false)
        Assert.assertEquals(View.GONE, view.visibility)
    }

}