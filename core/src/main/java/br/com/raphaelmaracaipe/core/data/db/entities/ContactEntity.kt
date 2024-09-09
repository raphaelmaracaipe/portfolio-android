package br.com.raphaelmaracaipe.core.data.db.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "contacts")
@Parcelize
data class ContactEntity(
    @PrimaryKey
    @ColumnInfo("phone")
    @SerializedName("phone")
    val phone: String = "",
    @ColumnInfo("name")
    @SerializedName("name")
    val name: String? = null,
    @ColumnInfo("photo")
    @SerializedName("photo")
    val photo: String? = null,
    @ColumnInfo("status")
    @SerializedName("status")
    val status: String? = null
) : Parcelable