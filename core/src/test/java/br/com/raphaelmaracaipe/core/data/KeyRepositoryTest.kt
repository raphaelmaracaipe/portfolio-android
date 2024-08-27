package br.com.raphaelmaracaipe.core.data

import android.os.Build
import br.com.raphaelmaracaipe.core.data.sp.KeySP
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class KeyRepositoryTest {

    private lateinit var keySP: KeySP
    private lateinit var keyRepository: KeyRepository
    private lateinit var keysDefault: KeysDefault

    @Before
    fun setUp() {
        keySP = mockk()
        keysDefault = mockk()
        keyRepository = KeyRepositoryImpl(keySP, keysDefault)
    }

    @Test
    fun `when saved key`() {
        every { keySP.get() } returns "key"

        try {
            val keySaved = keyRepository.get()
            assertEquals("key", keySaved)
        } catch (_: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when saved key but not exist saved get default`() {
        every { keySP.get() } returns ""
        every { keysDefault.key } returns "aaa"

        try {
            val keySaved = keyRepository.get()
            assertEquals("aaa", keySaved)
        } catch (_: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when key saved`() {
        every { keySP.save(any()) } returns Unit

        try {
            keyRepository.saveKeyGenerated("test")
            assertTrue(true)
        } catch (_: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when check if exist key saved`() {
        every { keySP.get() } returns "test"

        try {
            val isExist = keyRepository.isExistKeyRegistered()
            assertTrue(isExist)
        } catch (_: Exception) {
            assertTrue(false)
        }
    }

}