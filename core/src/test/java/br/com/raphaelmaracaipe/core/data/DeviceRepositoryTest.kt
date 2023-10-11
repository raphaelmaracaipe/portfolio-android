package br.com.raphaelmaracaipe.core.data

import android.os.Build
import br.com.raphaelmaracaipe.core.TestApplication
import br.com.raphaelmaracaipe.core.data.sp.DeviceIdSPImpl
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.externals.SpKeyDefault
import br.com.raphaelmaracaipe.core.security.CryptoHelperImpl
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, sdk = [Build.VERSION_CODES.M])
class DeviceRepositoryTest {

    private lateinit var deviceRepository: DeviceRepository

    @Before
    fun setUp() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        val cryptoHelper = CryptoHelperImpl()
        val keysDefault = KeysDefault("nDHj82ZWov6r4bnu", "30rBgU6kuVSHPNXX")
        val spKeysDefault = SpKeyDefault("AAA", "AAA", "AAA", "AAA")

        deviceRepository = DeviceRepositoryImpl(DeviceIdSPImpl(context, keysDefault, spKeysDefault, cryptoHelper))
    }

    @Test
    fun `when first start should register device id`() {
        val deviceIdSaved = deviceRepository.getDeviceIDSaved()
        assertNotEquals(deviceIdSaved, "")
    }
}