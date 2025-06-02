package ru.thegod.gitr.controllers

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import jakarta.inject.Inject
import ru.thegod.gitr.dto.GitrAddMembersListRequestDTO
import ru.thegod.gitr.dto.GitrEntityResponseDTO
import ru.thegod.gitr.service.GitrService


@Controller("/gits")
class GitrMembersController() {
    @Inject
    private lateinit var service: GitrService


    @Post(uri = "/addMember",
        consumes = arrayOf(MediaType.APPLICATION_FORM_URLENCODED),
        produces = arrayOf(MediaType.APPLICATION_JSON))
    fun addMemberInRepository(@Body id_list: GitrAddMembersListRequestDTO, request: HttpRequest<*>):
            HttpResponse<GitrEntityResponseDTO> {
        val token = request.cookies["AUTH-TOKEN"]
        return HttpResponse.created(service.addMemberInGitr(id_list,token))
    }
}