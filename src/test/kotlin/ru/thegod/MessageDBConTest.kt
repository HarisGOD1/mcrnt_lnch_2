package ru.thegod

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.thegod.messages.MessageEntity
import ru.thegod.messages.MessageRepository

@MicronautTest(environments = ["test"])
class MessageDBConTest() {


    @Inject
    lateinit var repository: MessageRepository

    @Test
    fun `findByPosition returns list of Messages`() {
        val messages = listOf(
            MessageEntity(id = null, messageContent = "Striker"),
            MessageEntity(id = null, messageContent = "Waldschmidt"),
            MessageEntity(id = null, messageContent = "Kroos")
        )
        repository.saveAll(messages)

        val repositoryReturnedMessages = repository.findAll()


        assertEquals(messages[0].messageContent,repositoryReturnedMessages[0].messageContent)
        assertEquals(messages[1].messageContent,repositoryReturnedMessages[1].messageContent)
        assertEquals(messages[2].messageContent,repositoryReturnedMessages[2].messageContent)

    }



}
