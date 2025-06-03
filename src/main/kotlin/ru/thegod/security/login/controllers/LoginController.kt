package ru.thegod.security.login.controllers

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import io.micronaut.views.View
import ru.thegod.security.login.service.LoginService
import java.net.URI

@Controller
class LoginController(private val loginService: LoginService) {
    // This controller receives user request
    // calls specified service method
    // and after returns response from service

    @Get("/login")
    @View("security/login.html")
    @Produces(MediaType.TEXT_PLAIN)
    fun loginView(){

    }

    @Post("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    fun login(username:String, password:String): HttpResponse<Any?> {
        val cookie /*is token or null*/ = loginService.loginReturnToken(username,password)?:
                                            return HttpResponse.unauthorized()

        return HttpResponse.redirect<Any?>(URI("/profile"))
            .cookie(cookie)
    }
    @Get("/logout")
    @Produces(MediaType.TEXT_PLAIN)
    fun performSingleLogout(request: HttpRequest<*>): HttpResponse<Any?> {
        val expToken = loginService.performLogout(request.cookies["AUTH-TOKEN"])
        expToken?: return HttpResponse.unauthorized()

        return HttpResponse.redirect<Any?>(URI("/logoutSuccess"))
            .cookie(expToken)
    }

    @Get("/logoutAll")
    @Produces(MediaType.TEXT_PLAIN)
    fun performAllLogout(request: HttpRequest<*>): HttpResponse<Any?> {
        val expToken = loginService.performLogoutAll(request.cookies["AUTH-TOKEN"])
        expToken?: return HttpResponse.unauthorized()


        return HttpResponse.redirect<Any?>(URI("/logoutSuccess"))
            .cookie(expToken)
    }

    @Get("/logoutSuccess")
    @Produces(MediaType.TEXT_PLAIN)
    fun ifLogoutSuccess(): HttpResponse<String> {
        return HttpResponse.created("logout")
    }


}