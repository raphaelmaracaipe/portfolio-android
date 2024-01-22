package br.com.raphaelmaracaipe.core.data

import android.os.Build
import br.com.raphaelmaracaipe.core.data.sp.SeedSP
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class SeedRepositoryTest {

    private lateinit var seedRepository: SeedRepository
    private lateinit var seedSP: SeedSP

    @Before
    fun setUp() {
        seedSP = mockk()
        seedRepository = SeedRepositoryImpl(seedSP)
    }

    @Test
    fun `when clean seed saved`() {
        every { seedSP.clean() } returns Unit
        try {
            seedRepository.cleanSeedSaved()
            assertTrue(true)
        } catch (e: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when get seed registered`() {
        every { seedSP.get() } returns "test"

        try {
            val seedSaved = seedRepository.get()
            assertEquals("test", seedSaved)
        } catch (e: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when get seed but not exist saved`() {
        every { seedSP.get() } returns ""
        every { seedSP.save(any()) } returns Unit

        try {
            val seedSaved = seedRepository.get()
            assertNotEquals("", seedSaved)
        } catch (e: Exception) {
            assertTrue(false)
        }
    }

}