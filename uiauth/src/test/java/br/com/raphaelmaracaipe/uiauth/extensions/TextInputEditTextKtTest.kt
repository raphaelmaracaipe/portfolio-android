package br.com.raphaelmaracaipe.uiauth.extensions

import android.content.Context
import android.os.Build
import android.telephony.PhoneNumberUtils
import android.util.Log
import br.com.raphaelmaracaipe.core.R
import com.google.android.material.textfield.TextInputEditText
import io.mockk.every
import io.mockk.mockkStatic
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
class TextInputEditTextKtTest {

    private lateinit var mContext: Context

    @Before
    fun setUp() {
        mContext = RuntimeEnvironment.getApplication().applicationContext
        mContext.setTheme(R.style.Theme_Portfolio)
    }

    @Test
    fun `when send number and return number formatted`() {
        mockkStatic(PhoneNumberUtils::class)
        every { PhoneNumberUtils.formatNumber("61982993098", "BR") } returns "(61) 98299 3098"

        val textInputEditText = TextInputEditText(mContext)
        textInputEditText.addMask("BR")
        textInputEditText.setText("99999999999")

        val textChanged = textInputEditText.text
        assertEquals("(99) 99999-9999", textChanged.toString())
    }

}