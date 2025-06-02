package ru.thegod.gitr.members.controllers

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import jakarta.inject.Inject
import ru.thegod.gitr.members.dto.GitrAddMembersListRequestDTO
import ru.thegod.gitr.core.dto.GitrEntityResponseDTO
import ru.thegod.gitr.members.services.GitrMembersService


@Controller("/gits")
class GitrMembersController() {
    @Inject
    private lateinit var mebmerService: GitrMembersService

    // AUTHORIZED
    @Post(uri = "/addMember",
        consumes = arrayOf(MediaType.APPLICATION_FORM_URLENCODED),
        produces = arrayOf(MediaType.APPLICATION_JSON))
    fun addMemberInRepository(@Body id_list: GitrAddMembersListRequestDTO, request: HttpRequest<*>):
            HttpResponse<GitrEntityResponseDTO> {
        val token = request.cookies["AUTH-TOKEN"]
        return HttpResponse.created(mebmerService.addMemberInGitr(id_list,token))
    }
}