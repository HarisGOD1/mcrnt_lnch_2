package ru.thegod.security.controllers

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.views.View
import io.micronaut.http.HttpResponse
import java.security.Principal

@Controller
@Secured(SecurityRule.IS_ANONYMOUS)
class HomeController {
    @Get("/home")
    @Produces(MediaType.TEXT_PLAIN)
    @View("home.html")
    fun index(principal: Principal?): HttpResponse<Any> {

        return HttpResponse.ok(mapOf("principal" to principal))
    }
    @Produces(MediaType.TEXT_PLAIN)
    @Get("/")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    fun index2(principal: Principal): String = principal.name
}