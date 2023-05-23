package br.com.raphaelmaracaipe.core.assets

import android.content.Context
import android.content.res.AssetManager
import android.graphics.drawable.Drawable
import java.util.Scanner

class AssetsImpl(
    private val context: Context,
    private val assetManager: AssetManager
) : Assets {

    override fun read(location: String): String {
        val inputStream = assetManager.open(location)
        val scanner = Scanner(inputStream).useDelimiter("\\A")

        val json = if (scanner.hasNext()) {
            scanner.next() ?: ""
        } else {
            ""
        }

        inputStream.close()
        return json
    }

    override fun list(location: String): Array<String> = assetManager.use { assetManager ->
        assetManager.list(location) as Array<String>
    }

    override fun drawableImage(locationImage: String): Drawable? {
        try {
            context.assets.open(locationImage).use {
                return Drawable.createFromStream(it, null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}