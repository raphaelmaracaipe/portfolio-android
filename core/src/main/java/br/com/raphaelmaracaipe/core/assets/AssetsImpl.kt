package br.com.raphaelmaracaipe.core.assets

import android.content.Context
import java.util.Scanner

class AssetsImpl(
    private val context: Context
): Assets {
    
    override fun read(location: String): String {
        val inputStream = context.assets.open(location)
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