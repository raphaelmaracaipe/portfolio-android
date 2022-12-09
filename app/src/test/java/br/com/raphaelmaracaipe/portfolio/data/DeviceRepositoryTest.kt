package br.com.raphaelmaracaipe.portfolio.data

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import br.com.raphaelmaracaipe.portfolio.data.api.device.DeviceAPI
import br.com.raphaelmaracaipe.portfolio.data.sp.device.DeviceSP
import br.com.raphaelmaracaipe.portfolio.data.sp.device.DeviceSPImpl
import br.com.raphaelmaracaipe.portfolio.data.sp.token.TokenSP
import br.com.raphaelmaracaipe.portfolio.data.sp.token.TokenSPImpl
import br.com.raphaelmaracaipe.portfolio.utils.regex.RegexGenerateImpl
import br.com.raphaelmaracaipe.portfolio.utils.security.encryptDecrypt.EncryptDecryptImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.M])
@RunWith(RobolectricTestRunner::class)
class DeviceRepositoryTest {

    private lateinit var deviceRepository: DeviceRepository
    private lateinit var deviceAPI: DeviceAPI
    private lateinit var tokenSP: TokenSP

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        deviceAPI = mockk()
        tokenSP = TokenSPImpl(context, EncryptDecryptImpl())

        deviceRepository = DeviceRepository(
            deviceAPI,
            tokenSP,
            RegexGenerateImpl()
        )
    }

    @Test
    fun `when send information about device`() = runBlocking {
        coEvery { deviceAPI.sendInformationAboutDevice(any()) } returns true
        val returnAfterTest = deviceRepository.sendInformationAboutDevice()
        Assert.assertTrue(returnAfterTest)
    }

    @Test
    fun `when send information about but already exist in db`() = runBlocking {
        coEvery { deviceAPI.sendInformationAboutDevice(any()) } returns true

        tokenSP.savedKeyOfCommunications("a")
        val returnAfterTest = deviceRepository.sendInformationAboutDevice()

        Assert.assertTrue(returnAfterTest)
    }

    @Before
    fun before() {
        tokenSP.clearAll()
    }

}