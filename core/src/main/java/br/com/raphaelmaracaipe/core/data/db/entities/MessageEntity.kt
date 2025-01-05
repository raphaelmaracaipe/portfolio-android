package br.com.raphaelmaracaipe.core.data.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Entity(
    tableName = "messages",
    foreignKeys = [ForeignKey(
        entity = ContactEntity::class,
        parentColumns = ["phone"],
        childColumns = ["phone"],
        onDelete = ForeignKey.CASCADE
    )]
)
@Parcelize
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val phone: String = "",
    val message: String = "",
    val dateTime: Long = Date().time
) : Parcelable