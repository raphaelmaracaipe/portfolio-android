package br.com.raphaelmaracaipe.core.data

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.TestApplication
import br.com.raphaelmaracaipe.core.data.sp.SeedSP
import br.com.raphaelmaracaipe.core.data.sp.SeedSPImpl
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, sdk = [Build.VERSION_CODES.M])
class SeedRepositoryTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var seedSp: SeedSP
    private lateinit var seedRepository: SeedRepository

    @Before
    fun setUp() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        seedSp = SeedSPImpl(context)
        seedRepository = SeedRepositoryImpl(seedSp)
    }

    @Test
    fun `when save seed should check value when call get`() {
        val seedSaved = seedRepository.get()
        assertNotEquals("", seedSaved)
    }

    @Test
    fun `when clean seed should not exist data saved`() {
        seedRepository.cleanSeedSaved()
        val seedSaved = seedSp.get()
        assertEquals("", seedSaved)
    }

    @After
    fun tearDown() {
        seedSp.clean()
    }

}