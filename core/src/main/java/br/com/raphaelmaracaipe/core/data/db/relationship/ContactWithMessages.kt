package br.com.raphaelmaracaipe.core.data.db.relationship

import androidx.room.Embedded
import androidx.room.Relation
import br.com.raphaelmaracaipe.core.data.db.entities.ContactEntity
import br.com.raphaelmaracaipe.core.data.db.entities.MessageEntity

data class ContactWithMessages(
    @Embedded
    val contactEntity: ContactEntity,
    @Relation(
        parentColumn = "phone",
        entityColumn = "phone"
    )
    val messages: List<MessageEntity>
)