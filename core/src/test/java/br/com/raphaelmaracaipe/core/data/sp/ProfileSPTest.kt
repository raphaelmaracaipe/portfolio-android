package br.com.raphaelmaracaipe.core.data.sp

import android.os.Build
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.externals.SpKeyDefault
import br.com.raphaelmaracaipe.core.security.CryptoHelper
import br.com.raphaelmaracaipe.core.utils.Strings
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class ProfileSPTest {

    private lateinit var profileSP: ProfileSP
    private val cryptoHelper: CryptoHelper = mockk()

    @Before
    fun setUp() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        val spKeyDefault = SpKeyDefault(
            Strings.generateStringRandom(10),
            Strings.generateStringRandom(10),
            Strings.generateStringRandom(10),
            Strings.generateStringRandom(10),
            Strings.generateStringRandom(10),
            Strings.generateStringRandom(10),
            Strings.generateStringRandom(10),
            Strings.generateStringRandom(10)
        )
        val keysDefault = KeysDefault(
            Strings.generateStringRandom(10),
            Strings.generateStringRandom(10)
        )

        profileSP = ProfileSPImpl(context, keysDefault, spKeyDefault, cryptoHelper)
    }

    @Test
    fun `when check if profile mark with exist`() {
        every { cryptoHelper.encrypt(any(), any(), any()) } returns "test"

        profileSP.markWithExistProfile()
        assertTrue(profileSP.isExistProfileSaved())
    }

}