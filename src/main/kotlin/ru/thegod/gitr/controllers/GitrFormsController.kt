package ru.thegod.gitr.controllers

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
//import io.micronaut.security.annotation.Secured
//import io.micronaut.security.rules.SecurityRule
import io.micronaut.views.View
import jakarta.inject.Inject
import ru.thegod.gitr.service.GitrService
import ru.thegod.security.cookie.CookieValidator
import java.util.*

@Controller("/gits")
class GitrFormsController() {
    @Inject
    private lateinit var service: GitrService
    @Inject
    private lateinit var cookieValidator: CookieValidator


    @View("gitr/addMemberForm.html")
    @Get("/form/addMember/{id}")
    @Produces(MediaType.TEXT_HTML)
    fun addMemberForm(id: UUID,request: HttpRequest<*>): HttpResponse<Any> {
        val user = cookieValidator.returnUserIfAuthTokenValid(request.cookies["AUTH-TOKEN"])
        if (user==null)
            return HttpResponse.unauthorized()

        val gitr = service.getGitrById(id)?:return HttpResponse.badRequest()
        println(gitr)
        if(gitr.gitrOwnerName!=user.username)
            return HttpResponse.badRequest()


        return HttpResponse.ok(mapOf("repos" to gitr))
    }

    @View("gitr/saveGitR.html")
    @Get("/form/saveGitr")
    @Produces(MediaType.TEXT_HTML)
//    @Secured(SecurityRule.IS_AUTHENTICATED)
    fun getSaveForm(): HttpResponse<String> {
        return HttpResponse.ok()
    }

}