package br.com.raphaelmaracaipe.core.assets

import android.content.res.AssetManager
import android.os.Build
import io.mockk.every
import io.mockk.mockk
import org.json.JSONObject
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.io.ByteArrayInputStream

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class AssetsTest {

    private lateinit var assertManager: AssetManager
    private lateinit var asserts: Assets

    @Before
    fun setUp() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        assertManager = mockk()
        asserts = AssetsImpl(context, assertManager)
    }

    @Test
    fun `when get value in the assets and exist data`() {
        val jsonMocked = JSONObject().apply {
            put("test", 1)
        }.toString()

        val mockInputStream = ByteArrayInputStream(jsonMocked.toByteArray())
        every { assertManager.open(any()) } returns mockInputStream

        val returnAssets = asserts.read("test")
        val containsData = returnAssets.contains("test")
        assertTrue(containsData)
    }

    @Test
    fun `when get list of datas in assets`() {
        every { assertManager.list(any()) } returns arrayOf("test1", "test2")
        every { assertManager.close() } returns Unit

        val returnsAssetsList = asserts.list("test")
        assertEquals(2, returnsAssetsList.size)
    }

}