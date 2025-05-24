package ru.thegod.messages

import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.MappedProperty
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*


@MappedEntity
@Table(name="entity_msg")
data class MessageEntity(@Id @GeneratedValue
                        val id: UUID? =null,
                         @MappedProperty("messagecontent")
                         var messageContent: String
//                         @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//                         val someLocalDateTime: LocalDateTime

                         ) {
    override fun toString(): String {
        return "id:$id| $messageContent"
    }
}