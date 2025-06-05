package ru.thegod.gitr.core.controllers

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import jakarta.inject.Inject
import ru.thegod.gitr.core.dto.GitrEntityCreationRequestDTO
import ru.thegod.gitr.core.dto.GitrEntityResponseDTO
import ru.thegod.gitr.core.services.GitrCreationService
import java.util.*

@Controller("/gits")
class GitrCreationController() {
    @Inject
    private lateinit var service: GitrCreationService

    // AUTHORIZED
    @Post(uri = "/save",
        consumes = arrayOf(MediaType.APPLICATION_FORM_URLENCODED),
        produces = arrayOf(MediaType.APPLICATION_JSON))
    fun saveRepository(@Body gitrCreationRequestDTO: GitrEntityCreationRequestDTO, request: HttpRequest<*>):
            HttpResponse<GitrEntityResponseDTO> {
        return HttpResponse.created(service.saveGitr(gitrCreationRequestDTO,request.cookies["AUTH-TOKEN"]))
    }


}