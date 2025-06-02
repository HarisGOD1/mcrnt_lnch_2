package ru.thegod.gitr.controllers

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
//import io.micronaut.security.annotation.Secured
//import io.micronaut.security.rules.SecurityRule
import jakarta.inject.Inject
import ru.thegod.gitr.dto.GitrEntityRequestDTO
import ru.thegod.gitr.dto.GitrEntityResponseDTO
import ru.thegod.gitr.service.GitrService
import ru.thegod.security.cookie.CookieValidator
import java.security.Principal
import java.util.*

@Controller("/gits")
class GitrController() {
    @Inject
    private lateinit var service: GitrService

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


    // AUTHORIZED
    @Post(uri = "/save",
        consumes = arrayOf(MediaType.APPLICATION_FORM_URLENCODED),
        produces = arrayOf(MediaType.APPLICATION_JSON))
    fun saveRepository(@Body gitrEntityRequestDTO: GitrEntityRequestDTO,request: HttpRequest<*>):
            HttpResponse<GitrEntityResponseDTO> {
        return HttpResponse.created(service.saveGitr(gitrEntityRequestDTO,request.cookies["AUTH-TOKEN"]))
    }


}