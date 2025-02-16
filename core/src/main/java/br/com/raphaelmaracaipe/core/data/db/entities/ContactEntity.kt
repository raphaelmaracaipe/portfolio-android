package br.com.raphaelmaracaipe.core.data.db.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
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
    @ColumnInfo("reminder")
    @SerializedName("reminder")
    val reminder: String? = null,
    @ColumnInfo("key")
    @SerializedName("key")
    val key: String? = null,
    @ColumnInfo("lastSeen")
    @SerializedName("lastSeen")
    val lastSeen: Long? = null
) : Parcelable {

    fun toJSON(): String = Gson().toJson(this) ?: "{}"

}