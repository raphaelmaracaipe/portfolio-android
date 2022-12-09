package br.com.raphaelmaracaipe.portfolio.utils.device

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
class DeviceNetworkTest {

    private lateinit var deviceNetwork: DeviceNetwork

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        deviceNetwork = DeviceNetworkImpl(context)
    }

    @Config(sdk = [Build.VERSION_CODES.M])
    @Test
    fun `test device connected with version M`() {
        val isNetwork = deviceNetwork.isNetworkConnected()
        Assert.assertTrue(isNetwork)
    }

    @Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
    @Test
    fun `test device connected with version N`() {
        val isNetwork = deviceNetwork.isNetworkConnected()
        Assert.assertTrue(isNetwork)
    }

}