package ru.thegod.security.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.views.View
import ru.thegod.security.User
import ru.thegod.security.UserRepository
import java.security.Principal

@Controller
class LoginController(private val repository: UserRepository) {

    @Get("/profile")
    @Produces(MediaType.TEXT_PLAIN)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    fun profile(principal: Principal): String = "${principal.name}\n${principal}"

    //    @Post("/login")
//    fun login(username:String, password:String){
//
//    }
    @Get("/reg/{un}/{ph}")
    @Produces(MediaType.TEXT_PLAIN)
    @Secured(SecurityRule.IS_ANONYMOUS)
    fun register_dumb(un:String, ph:String){
        repository.save(User(null,un,ph))
    }

    @Get("/login")
    @View("security/login.html")
    @Produces(MediaType.TEXT_PLAIN)
    @Secured(SecurityRule.IS_ANONYMOUS)
    fun login_dumb(){

    }

    @Get("/home")
    @Produces(MediaType.TEXT_PLAIN)
    @View("home.html")
    @Secured(SecurityRule.IS_ANONYMOUS)
    fun index(principal: Principal?): HttpResponse<Any> {

        return HttpResponse.ok(mapOf("principal" to principal))
    }


}