package br.com.raphaelmaracaipe.portfolio.data.sp.device

import android.os.Build
import br.com.raphaelmaracaipe.portfolio.utils.security.encryptDecrypt.EncryptDecryptImpl
import org.junit.After
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
        deviceSP = DeviceSPImpl(context, EncryptDecryptImpl())
    }

    @Test
    fun `when generate uuid new`() {
        val uuid = deviceSP.getUUID()
        Assert.assertNotEquals("", uuid)
    }

    @Test
    fun `when generate uuid saved`() {
        deviceSP.getUUID()
        val uuid = deviceSP.getUUID()
        Assert.assertNotEquals("", uuid)
    }

    @After
    fun after() {
        deviceSP.clearAll()
    }

}