package ru.thegod.userRepositories

import io.micronaut.core.annotation.Introspected
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.serde.annotation.Serdeable
import io.micronaut.views.View

@Controller("/gits")
class GitRepositoryController(private val repository: GitRRepository) {

    @Get("/getAll")
    fun getAllRepositories(): HttpResponse<String> {
        return HttpResponse.created(repository.findAll().toString())
    }

    @View("saveGitR.html")
    @Get("/form")
    @Produces(MediaType.TEXT_HTML)
    fun getSaveForm(): HttpResponse<String> {
        return HttpResponse.ok()

    }


    @Post(uri = "/save", consumes = arrayOf(MediaType.APPLICATION_FORM_URLENCODED),
        produces = arrayOf(MediaType.APPLICATION_JSON))
    fun saveRepository(@Body gitRepositoryEntityRequestDTO:Request):
            HttpResponse<GitRepositoryEntityResponseDTO> {

        return HttpResponse.created(repository.save(
            GitRepositoryEntity(gitRepositoryEntityRequestDTO.gitRepositoryName,
            gitRepositoryEntityRequestDTO.gitOwnerName,
            gitRepositoryEntityRequestDTO.publicity,
            gitRepositoryEntityRequestDTO.repositoryDescription)
        ).toResponseDTO())
    }

    @Introspected
    @Serdeable
    data class Request(val gitRepositoryName: String, val gitOwnerName: String,
                       val publicity: Boolean, val repositoryDescription:String?)


}