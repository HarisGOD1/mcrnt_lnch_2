package ru.thegod.gitr.controllers

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
//import io.micronaut.security.annotation.Secured
//import io.micronaut.security.rules.SecurityRule
import jakarta.inject.Inject
import ru.thegod.gitr.dto.GitrEntityRequestDTO
import ru.thegod.gitr.dto.GitrEntityResponseDTO
import ru.thegod.gitr.service.GitrService
import java.security.Principal
import java.util.*

@Controller("/gits")
class GitrController() {
    @Inject
    private lateinit var service: GitrService


    @Get("/getAll")
//    @Secured(SecurityRule.IS_ANONYMOUS)     // ONLY FOR DEVELOPMENT
    @Produces(MediaType.APPLICATION_JSON)
    fun getAllRepositories(): HttpResponse<List<GitrEntityResponseDTO>> {
        return HttpResponse.created(service.getAllGitrEntities_toDTO())
    }


    @Get("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
//    @Secured(SecurityRule.IS_ANONYMOUS)
    fun getById(id: UUID): HttpResponse<GitrEntityResponseDTO> {
        return HttpResponse.created(service.getGitrById(id))
    }


    @Post(uri = "/save",
        consumes = arrayOf(MediaType.APPLICATION_FORM_URLENCODED),
        produces = arrayOf(MediaType.APPLICATION_JSON))
//    @Secured(SecurityRule.IS_AUTHENTICATED)
    fun saveRepository(@Body gitrEntityRequestDTO: GitrEntityRequestDTO,principal:Principal):
            HttpResponse<GitrEntityResponseDTO> {

        return HttpResponse.created(service.saveGitr(gitrEntityRequestDTO,principal))
    }


}