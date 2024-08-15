package br.com.raphaelmaracaipe.core.data.db.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Entity(tableName = "code_countries")
@Parcelize
data class CodeCountryEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo("country_name")
    @SerializedName("country_name")
    val countryName: String? = null,
    @ColumnInfo("code_country")
    @SerializedName("code_country")
    val codeCountry: String? = null,
    @ColumnInfo("code_ison")
    @SerializedName("code_ison")
    val codeIson: String? = null,
) : Parcelable