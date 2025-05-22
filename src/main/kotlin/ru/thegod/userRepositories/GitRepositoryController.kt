package ru.thegod.userRepositories

import io.micronaut.core.annotation.Introspected
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.serde.annotation.Serdeable
import io.micronaut.views.View
import jakarta.inject.Inject
import ru.thegod.messages.MessageEntity

@Controller("/gits")
class GitRepositoryController() {
    @Inject
    private lateinit var service: GitRepositoryService


    @Get("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    fun getAllRepositories(): HttpResponse<List<GitRepositoryEntityResponseDTO>> {
        return HttpResponse.created(service.getAllGitRepositories())
    }


    @Get("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun setMessage(id:Long): HttpResponse<GitRepositoryEntityResponseDTO> {
        return HttpResponse.created(service.getGitRepositoryById(id))
    }


    @View("saveGitR.html")
    @Get("/form")
    @Produces(MediaType.TEXT_HTML)
    fun getSaveForm(): HttpResponse<String> {
        return HttpResponse.ok()

    }


    @Post(uri = "/save",
        consumes = arrayOf(MediaType.APPLICATION_FORM_URLENCODED),
        produces = arrayOf(MediaType.APPLICATION_JSON))
    fun saveRepository(@Body gitRepositoryEntityRequestDTO:GitRepositoryEntityRequestDTO):
            HttpResponse<GitRepositoryEntityResponseDTO> {

        return HttpResponse.created(service.saveGitRepository(gitRepositoryEntityRequestDTO))
    }

    @Post(uri = "/addMember",
        consumes = arrayOf(MediaType.APPLICATION_FORM_URLENCODED),
        produces = arrayOf(MediaType.APPLICATION_JSON))
    fun addMemberInRepository(@Body id_list: GitRepositoryAddMemberListRequestDTO):
            HttpResponse<GitRepositoryEntityResponseDTO> {

        return HttpResponse.created(service.addMemberInGitRepository(id_list))
    }

}