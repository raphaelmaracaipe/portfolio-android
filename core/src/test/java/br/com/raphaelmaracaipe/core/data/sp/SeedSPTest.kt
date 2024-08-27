package br.com.raphaelmaracaipe.core.data.sp

import android.os.Build
import br.com.raphaelmaracaipe.core.TestApplication
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
class SeedSPTest {

    private lateinit var seedSP: SeedSP

    @Before
    fun setUp() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        seedSP = SeedSPImpl(context)
    }

    @Test
    fun `when save values in sp should call method with param`() {
        seedSP.save("test")
        val seedSaved = seedSP.get()
        assertEquals(seedSaved, "test")
    }

    @Test
    fun `when clean data saved in sp should return value empty`() {
        seedSP.save("test")
        seedSP.clean()
        val seedSaved = seedSP.get()
        assertEquals(seedSaved, "")
    }


}