package ru.thegod.gitr.Controllers

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.views.View
import jakarta.inject.Inject
import ru.thegod.gitr.DTO.GitrAddMembersListRequestDTO
import ru.thegod.gitr.DTO.GitrEntityRequestDTO
import ru.thegod.gitr.DTO.GitrEntityResponseDTO
import ru.thegod.gitr.service.GitrService
import java.util.*

@Controller("/gits")
class GitrFormsController() {
    @Inject
    private lateinit var service: GitrService


    @View("addMemberForm.html")
    @Get("/form/addMember/{id}")
    @Produces(MediaType.TEXT_HTML)
    fun addMemberForm(id: UUID): HttpResponse<Any> {
        return HttpResponse.ok(mapOf("repos" to service.getGitrById(id)))
    }

    @View("saveGitR.html")
    @Get("/form/saveGitr")
    @Produces(MediaType.TEXT_HTML)
    fun getSaveForm(): HttpResponse<String> {
        return HttpResponse.ok()
    }

}