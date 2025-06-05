package ru.thegod.gitr.core.controllers

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.views.View
import ru.thegod.gitr.members.services.GitrMembersService
import ru.thegod.gitr.core.services.GitrCreationService
import ru.thegod.security.cookies.service.CookieValidator
import java.util.*

@Controller("/gits")
class GitrFormsController(private var service: GitrCreationService,
                          private var membersService: GitrMembersService,
                          private  var cookieValidator: CookieValidator) {

    // AUTHORIZED
    @View("gitr/addMemberForm.html")
    @Get("/form/addMember/{id}")
    @Produces(MediaType.TEXT_HTML)
    fun addMemberForm(id: UUID,request: HttpRequest<*>): HttpResponse<Any> {
        return membersService.getMemberListFromGitr(id,request.cookies["AUTH-TOKEN"])
    }


    // UNAUTHORIZED
    @View("gitr/saveGitR.html")
    @Get("/form/saveGitr")
    @Produces(MediaType.TEXT_HTML)
    fun getSaveForm(): HttpResponse<String> {
        return HttpResponse.ok()
    }

}