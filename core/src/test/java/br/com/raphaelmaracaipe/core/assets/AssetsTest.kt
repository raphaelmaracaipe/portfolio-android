package br.com.raphaelmaracaipe.core.assets

import android.content.res.AssetManager
import android.os.Build
import br.com.raphaelmaracaipe.core.TestApplication
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, sdk = [Build.VERSION_CODES.M])
class AssetsTest {

    @Test
    fun `when put location of json in assets should return success`() {
        val jsonObject = JSONObject()
        jsonObject.put("test", "is test")
        val jsonString = jsonObject.toString()

        val inputStream = jsonString.byteInputStream()
        val assetsManager = mockk<AssetManager>()
        every { assetsManager.open("test.json") } returns inputStream

        val mAssets: Assets = AssetsImpl(assetsManager)
        val result = mAssets.read("test.json")

        assertEquals(jsonString, result)
    }

}