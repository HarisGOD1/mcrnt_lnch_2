package ru.thegod.gitr.core.controllers

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import jakarta.inject.Inject
import ru.thegod.gitr.core.dto.GitrEntityResponseDTO
import ru.thegod.gitr.core.services.GitrViewService
import java.util.UUID

@Controller("/gits")
class GitrViewController {

    @Inject
    private lateinit var service: GitrViewService

    // UNAUTHORIZED
    @Get("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    fun getAllRepositories(): HttpResponse<List<GitrEntityResponseDTO>> {
        return HttpResponse.created(service.getAllGitrEntities_toDTO())
    }


    // UNAUTHORIZED
    @Get("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getById(id: UUID): HttpResponse<GitrEntityResponseDTO> {
        return HttpResponse.created(service.getGitrById(id))
    }
}