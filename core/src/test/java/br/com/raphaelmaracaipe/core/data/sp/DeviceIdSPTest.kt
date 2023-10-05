package br.com.raphaelmaracaipe.core.data.sp

import android.content.Context
import android.os.Build
import br.com.raphaelmaracaipe.core.TestApplication
import br.com.raphaelmaracaipe.core.network.utils.KeysDefault
import br.com.raphaelmaracaipe.core.security.CryptoHelperImpl
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, sdk = [Build.VERSION_CODES.M])
class DeviceIdSPTest {

    private lateinit var deviceIdSP: DeviceIdSP

    @Before
    fun setUp() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        val cryptoHelper = CryptoHelperImpl()
        val keysDefault = KeysDefault("nDHj82ZWov6r4bnu", "30rBgU6kuVSHPNXX")

        deviceIdSP = DeviceIdSPImpl(context, keysDefault, cryptoHelper)
    }

    @Test
    fun `when register device and get value`() {
        val deviceId = "TEST OF SAVED"
        deviceIdSP.save(deviceId)
        val deviceIdSaved = deviceIdSP.get()

        assertEquals(deviceId, deviceIdSaved)
    }

}