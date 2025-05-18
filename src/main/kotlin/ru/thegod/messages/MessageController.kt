package ru.thegod.messages

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces

@Controller("/msg")
class MessageController(private val repository: MessageRepository) {

    @Get("/get")
    @Produces(MediaType.TEXT_PLAIN)
    fun getMessage(): HttpResponse<String> {
        return HttpResponse.created(repository.findAll().map{"${it.id}| ${it.messageContent}\n"}.joinToString(separator = ""))
    }

    @Get("/set/{msg}")
    @Produces(MediaType.TEXT_PLAIN)
    fun setMessage(msg:String): HttpResponse<String> {
        var savedId = repository.save(MessageEntity(messageContent = msg)).id
        return HttpResponse.created("message saved, id:$savedId")
    }
}