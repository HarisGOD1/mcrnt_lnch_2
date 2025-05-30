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
import ru.thegod.security.service.passwordEncryptService
import java.net.URI

@Controller
class LoginController(private val repository: UserRepository,
                      private val cookieTokenProvider: CookieTokenProvider,
                      private val cookieValidator: CookieValidator) {

//    @Get("/profile")
//    @Produces(MediaType.TEXT_PLAIN)
////    @Secured(SecurityRule.IS_AUTHENTICATED)
//    fun profile(principal: Principal): String = "${principal.name}\n${principal}"



    @Get("/reg/{un}/{ph}")
    @Produces(MediaType.TEXT_PLAIN)
    fun register_dumb(un:String, ph:String){
        repository.save(User(null,un,ph))
    }
    @Get("/login")
    @View("security/login.html")
    @Produces(MediaType.TEXT_PLAIN)
    fun login_dumb(){

    }
    @Post("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    fun login(username:String, password:String):HttpResponse<Any>{
        val user = repository.findByUsername(username) ?: return HttpResponse.unauthorized()

        if(passwordEncryptService.passwordToHash(password)==user.passwordHash)
            return HttpResponse.redirect<Any?>(URI("/profile")).cookie(cookieTokenProvider.releaseCookie(username,"user"))
        else return HttpResponse.unauthorized()
    }
    @Get("/profile")
    @Produces(MediaType.TEXT_PLAIN)
    fun profilePage(request: HttpRequest<*>): HttpResponse<String>{
        val token = request.cookies["AUTH-TOKEN"]?.value

        return if (token != null) {
            val username = cookieValidator.returnUsernameIfAuthCookieValid(token)
            if (username!=null) {
                return HttpResponse.ok("Token is valid, ${username}")
            }
            return HttpResponse.unauthorized()
        }
        else{
            return HttpResponse.unauthorized()
        }

    }


}