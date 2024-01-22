package br.com.raphaelmaracaipe.core.data

import android.os.Build
import br.com.raphaelmaracaipe.core.data.sp.DeviceIdSP
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class DeviceRepositoryTest {

    private lateinit var deviceRepository: DeviceRepository
    private lateinit var deviceIdSP: DeviceIdSP

    @Before
    fun setUp() {
        deviceIdSP = mockk()
        deviceRepository = DeviceRepositoryImpl(deviceIdSP)
    }

    @Test
    fun `when exist device id saved`() {
        every { deviceIdSP.get() } returns "test"

        val deviceIdSaved = deviceRepository.getDeviceIDSaved()
        assertEquals("test", deviceIdSaved)
    }

    @Test
    fun `when not exist device id saved`() {
        every { deviceIdSP.get() } returns ""
        every { deviceIdSP.save(any()) } returns Unit

        val deviceIdSaved = deviceRepository.getDeviceIDSaved()
        assertNotEquals("", deviceIdSaved)
    }

}