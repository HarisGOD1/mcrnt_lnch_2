package ru.thegod.security.controllers

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import ru.thegod.security.service.ProfileService

@Controller
class ProfileController(private val profileService: ProfileService) {

    @Get("/profile")
    @Produces(MediaType.TEXT_PLAIN)
    fun profilePage(request: HttpRequest<*>): HttpResponse<String> {
        return profileService.profilePage(request)

    }


}