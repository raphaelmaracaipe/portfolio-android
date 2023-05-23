package br.com.raphaelmaracaipe.core.assets

import android.content.Context
import android.content.res.AssetManager
import android.os.Build
import br.com.raphaelmaracaipe.core.R
import br.com.raphaelmaracaipe.core.TestApplication
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import junit.framework.TestCase.assertEquals
import org.json.JSONObject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, sdk = [Build.VERSION_CODES.M])
class AssetsTest {

    private lateinit var mContext: Context

    @Before
    fun setUp() {
        mContext = RuntimeEnvironment.getApplication().applicationContext
    }

    @Test
    fun `when put location of json in assets should return success`() {
        val jsonObject = JSONObject()
        jsonObject.put("test", "is test")
        val jsonString = jsonObject.toString()

        val inputStream = jsonString.byteInputStream()
        val assetsManager = mockk<AssetManager>()
        every { assetsManager.open("test.json") } returns inputStream

        val mAssets: Assets = AssetsImpl(mContext, assetsManager)
        val result = mAssets.read("test.json")

        assertEquals(jsonString, result)
    }

    @Test
    fun `when want get list of file in assets`() {
        val assetsManager = mockk<AssetManager>()
        every { assetsManager.list(any()) } returns arrayOf("test.png", "test1.png")
        every { assetsManager.close() } just runs

        val mAssets: Assets = AssetsImpl(mContext, assetsManager)
        val result = mAssets.list("/")

        assertEquals(2, result.size)
    }

}