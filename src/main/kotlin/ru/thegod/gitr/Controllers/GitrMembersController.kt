package ru.thegod.gitr.Controllers

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import jakarta.inject.Inject
import ru.thegod.gitr.DTO.GitrAddMembersListRequestDTO
import ru.thegod.gitr.DTO.GitrEntityResponseDTO
import ru.thegod.gitr.service.GitrService


@Controller("/gits")
class GitrMembersController() {
    @Inject
    private lateinit var service: GitrService


    @Post(uri = "/addMember",
        consumes = arrayOf(MediaType.APPLICATION_FORM_URLENCODED),
        produces = arrayOf(MediaType.APPLICATION_JSON))
    fun addMemberInRepository(@Body id_list: GitrAddMembersListRequestDTO):
            HttpResponse<GitrEntityResponseDTO> {
        return HttpResponse.created(service.addMemberInGitr(id_list))
    }
}