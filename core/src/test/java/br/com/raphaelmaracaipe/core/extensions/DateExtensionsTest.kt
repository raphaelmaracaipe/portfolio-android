package br.com.raphaelmaracaipe.core.extensions

import android.os.Build
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Date

@RunWith(RobolectricTestRunner::class)
class DateExtensionsTest {

    @Test
    @Config(sdk = [Build.VERSION_CODES.O])
    fun `Obtain date in timestamp O`() {
        val dataInTimestamp = Date().getTimeStamp()
        assertNotEquals(0L, dataInTimestamp)
    }

    @Test
    @Config(sdk = [Build.VERSION_CODES.N])
    fun `Obtain date in timestamp N`() {
        val dataInTimestamp = Date().getTimeStamp()
        assertNotEquals(0L, dataInTimestamp)
    }

}