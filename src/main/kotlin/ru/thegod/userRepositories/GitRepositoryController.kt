package ru.thegod.userRepositories

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import ru.thegod.messages.MessageRepository

@Controller("/gits")
class GitRepositoryController(private val repository: GitRepositoryRepository) {

    @Get("/git")
    fun getAllRepositories(): HttpResponse<String> {
        return HttpResponse.created("")
    }

    @Post("/save")
    fun saveRepository(): HttpResponse<String> {
//        GitRepositoryService.save();
        return HttpResponse.created("")
    }

}