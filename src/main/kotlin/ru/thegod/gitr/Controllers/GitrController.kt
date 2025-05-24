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
class GitrController() {
    @Inject
    private lateinit var service: GitrService


    @Get("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    fun getAllRepositories(): HttpResponse<List<GitrEntityResponseDTO>> {
        return HttpResponse.created(service.getAllGitrEntities_toDTO())
    }


    @Get("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun setMessage(id: UUID): HttpResponse<GitrEntityResponseDTO> {
        return HttpResponse.created(service.getGitrById(id))
    }


    @Post(uri = "/save",
        consumes = arrayOf(MediaType.APPLICATION_FORM_URLENCODED),
        produces = arrayOf(MediaType.APPLICATION_JSON))
    fun saveRepository(@Body gitrEntityRequestDTO: GitrEntityRequestDTO):
            HttpResponse<GitrEntityResponseDTO> {

        return HttpResponse.created(service.saveGitr(gitrEntityRequestDTO))
    }


}