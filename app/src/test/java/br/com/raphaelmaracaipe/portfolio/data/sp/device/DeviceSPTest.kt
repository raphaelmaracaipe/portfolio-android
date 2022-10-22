package br.com.raphaelmaracaipe.portfolio.data.sp.device

import android.os.Build
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.M])
@RunWith(RobolectricTestRunner::class)
class DeviceSPTest {

    private lateinit var deviceSP: DeviceSP

    @Before
    fun setUp() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        deviceSP = DeviceSPImpl(context)
    }

    @Test
    fun `when generate uuid`() {
        val uuid = deviceSP.getUUID()
        Assert.assertNotEquals("", uuid)
    }

}