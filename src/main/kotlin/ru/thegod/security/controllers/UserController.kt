package ru.thegod.security.controllers

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import io.micronaut.views.View
import ru.thegod.security.service.UserService

@Controller
class UserController(private val userService: UserService) {

    @Get("/registerForm")
    @View("security/register.html")
    @Produces(MediaType.TEXT_PLAIN)
    fun registerForm(){

    }


    @Post("/register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    fun registerNew(username:String,password:String):HttpResponse<Any?>{
        return userService.registerNewUser(username,password)

    }
}