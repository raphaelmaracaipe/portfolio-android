package br.com.raphaelmaracaipe.core.assets

import android.content.res.AssetManager
import java.util.Scanner

class AssetsImpl(
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

}