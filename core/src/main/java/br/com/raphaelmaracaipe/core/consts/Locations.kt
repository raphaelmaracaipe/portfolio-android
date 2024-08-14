package br.com.raphaelmaracaipe.core.consts

import android.os.Environment.DIRECTORY_DOCUMENTS
import android.os.Environment.getExternalStoragePublicDirectory
import java.io.File

object Locations {

    const val LOCATION_JSON_IN_ASSETS = "json/codes.json"
    val LOCATION_FOLDER_ROOT = File(
        getExternalStoragePublicDirectory(DIRECTORY_DOCUMENTS),
        "portfolio/"
    )
    val LOCATION_PROFILE = File("$LOCATION_FOLDER_ROOT/profile.jpg")

}