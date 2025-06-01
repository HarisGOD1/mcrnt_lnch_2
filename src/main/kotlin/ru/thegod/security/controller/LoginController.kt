package ru.thegod.security.controller

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
//import io.micronaut.security.annotation.Secured
//import io.micronaut.security.rules.SecurityRule
import io.micronaut.views.View
import ru.thegod.security.User
import ru.thegod.security.UserRepository
import ru.thegod.security.cookie.CookieTokenProvider
import ru.thegod.security.cookie.CookieValidator
import ru.thegod.security.service.LoginService
import ru.thegod.security.service.passwordEncryptService
import ru.thegod.security.service.passwordEncryptService.md5
import java.net.URI

@Controller
class LoginController(private val repository: UserRepository,
                      private val cookieTokenProvider: CookieTokenProvider,
                      private val cookieValidator: CookieValidator,
                      private val loginService: LoginService) {
    @Get("/reg/{un}/{ph}") // TO-DO: replace
    @Produces(MediaType.TEXT_PLAIN)
    fun register_dumb(un:String, ph:String){
        repository.save(User(null,un,ph.md5()))
    }
    @Get("/login")
    @View("security/login.html")
    @Produces(MediaType.TEXT_PLAIN)
    fun login_dumb(){

    }

    @Post("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    fun login(username:String, password:String):HttpResponse<Any>{
        return loginService.login(username,password)
    }
    @Get("/profile")
    @Produces(MediaType.TEXT_PLAIN)
    fun profilePage(request: HttpRequest<*>): HttpResponse<String>{
        return loginService.profilePage(request)

    }


}