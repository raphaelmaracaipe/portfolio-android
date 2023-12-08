package br.com.raphaelmaracaipe.core.data.sp

import android.os.Build
import br.com.raphaelmaracaipe.core.TestApplication
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.externals.SpKeyDefault
import br.com.raphaelmaracaipe.core.security.CryptoHelperImpl
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, sdk = [Build.VERSION_CODES.M])
class ProfileSPTest {

    private lateinit var profileSP: ProfileSP

    @Before
    fun setUp() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        val cryptoHelper = CryptoHelperImpl()
        val keysDefault = KeysDefault("nDHj82ZWov6r4bnu", "30rBgU6kuVSHPNXX")
        val spKeyDefault = SpKeyDefault(
            "AAA",
            "AAA",
            "AAA",
            "AAA",
            "AAA",
            "AAA",
            "AAA",
            "AAA"
        )

        profileSP = ProfileSPImpl(context, keysDefault, spKeyDefault, cryptoHelper)
    }

    @Test
    fun `when mark profile if exist should return to query`() {
        profileSP.markWithExistProfile()
        val isExist = profileSP.isExistProfileSaved()
        assertTrue(isExist)
    }


}