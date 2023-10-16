package br.com.raphaelmaracaipe.core.data

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.TestApplication
import br.com.raphaelmaracaipe.core.data.sp.KeySP
import br.com.raphaelmaracaipe.core.data.sp.KeySPImpl
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.externals.SpKeyDefault
import br.com.raphaelmaracaipe.core.security.CryptoHelperImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, sdk = [Build.VERSION_CODES.M])
class KeyRepositoryTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var keySP: KeySP
    private lateinit var keysDefault: KeysDefault

    @Before
    fun setUp() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        val cryptoHelper = CryptoHelperImpl()
        val spKeyDefault = SpKeyDefault("AAA", "AAA", "AAA", "AAA", "AAA", "AAA")
        keysDefault = KeysDefault("nDHj82ZWov6r4bnu", "30rBgU6kuVSHPNXX")

        keySP = KeySPImpl(context, keysDefault, spKeyDefault, cryptoHelper)
    }

    @Test
    fun `when get data saved should return data saved in sp and without data`() {
        val keyRepository = KeyRepositoryImpl(keySP, keysDefault)
        assertEquals(keysDefault.key, keyRepository.get())
    }

    @Test
    fun `when get data saved should return data saved in sp and with data`() {
        keySP.save("test")
        val keyRepository = KeyRepositoryImpl(keySP, keysDefault)
        assertEquals(keySP.get(), keyRepository.get())
    }

    @Test
    fun `when save value in sp should when call return data saved`() = runBlocking {
        val keyRepository = KeyRepositoryImpl(keySP, keysDefault)
        keyRepository.saveKeyGenerated("test")
        assertEquals(keySP.get(), keyRepository.get())
    }

    @Test
    fun `when check if exist data should return true or false`() = runBlocking {
        val keyRepository = KeyRepositoryImpl(keySP, keysDefault)
        assertFalse(keyRepository.isExistKeyRegistered())
    }

    @After
    fun tearDown() {
        keySP.clean()
    }

}