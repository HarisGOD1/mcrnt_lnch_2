package ru.thegod.security.controllers

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
//import io.micronaut.security.annotation.Secured
//import io.micronaut.security.rules.SecurityRule
import io.micronaut.views.View
import ru.thegod.security.UserRepository
import ru.thegod.security.cookies.CookieTokenProvider
import ru.thegod.security.cookies.CookieValidator
import ru.thegod.security.service.LoginService

@Controller
class LoginController(private val repository: UserRepository,
                      private val cookieTokenProvider: CookieTokenProvider,
                      private val cookieValidator: CookieValidator,
                      private val loginService: LoginService) {
    @Get("/login")
    @View("security/login.html")
    @Produces(MediaType.TEXT_PLAIN)
    fun loginView(){

    }

    @Post("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    fun login(username:String, password:String):HttpResponse<Any>{
        return loginService.login(username,password)
    }
    @Get("/logout")
    @Produces(MediaType.TEXT_PLAIN)
    fun performSingleLogout(request: HttpRequest<*>): HttpResponse<Any?>{
        return loginService.performLogout(request.cookies["AUTH-TOKEN"])
    }
    @Get("/logoutAll")
    @Produces(MediaType.TEXT_PLAIN)
    fun performAllLogout(request: HttpRequest<*>): HttpResponse<Any?>{
        return loginService.performLogoutAll(request.cookies["AUTH-TOKEN"])
    }

    @Get("/logoutSuccess")
    @Produces(MediaType.TEXT_PLAIN)
    fun ifLogoutSuccess():HttpResponse<String>{
        return HttpResponse.created("logout")
    }


}