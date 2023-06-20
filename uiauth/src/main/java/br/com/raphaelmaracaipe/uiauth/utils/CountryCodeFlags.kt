package br.com.raphaelmaracaipe.uiauth.utils

import android.graphics.drawable.Drawable
import br.com.raphaelmaracaipe.core.assets.Assets
import br.com.raphaelmaracaipe.uiauth.models.CodeCountry

object CountryCodeFlags {

    fun getFlag(assets: Assets, country: CodeCountry): Drawable? {
        country.codeIson?.split(" / ")?.let { codeCountryIsonSplit ->
            try {
                val codeCountryIson = codeCountryIsonSplit[0].lowercase()
                return assets.drawableImage(
                    "flags/${codeCountryIson}.png"
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }

}