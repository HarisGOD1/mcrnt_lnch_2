package ru.thegod.gitr.controllers

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.views.View
import jakarta.inject.Inject
import ru.thegod.gitr.service.GitrService
import java.util.*

@Controller("/gits")
@Secured(SecurityRule.IS_ANONYMOUS)
class GitrFormsController() {
    @Inject
    private lateinit var service: GitrService


    @View("gitr/addMemberForm.html")
    @Get("/form/addMember/{id}")
    @Produces(MediaType.TEXT_HTML)
    fun addMemberForm(id: UUID): HttpResponse<Any> {
        return HttpResponse.ok(mapOf("repos" to service.getGitrById(id)))
    }


    @View("gitr/saveGitR.html")
    @Get("/form/saveGitr")
    @Produces(MediaType.TEXT_HTML)
    fun getSaveForm(): HttpResponse<Any> {
        return HttpResponse.ok()
    }

}