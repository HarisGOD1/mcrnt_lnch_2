package ru.thegod.security.service

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import io.micronaut.views.View
import jakarta.inject.Singleton
import ru.thegod.security.User
import ru.thegod.security.UserRepository
import ru.thegod.security.cookie.CookieTokenProvider
import ru.thegod.security.cookie.CookieValidator
import ru.thegod.security.service.passwordEncryptService.md5
import java.net.URI

@Singleton
class LoginService(private val repository: UserRepository,
                   private val cookieTokenProvider: CookieTokenProvider,
                   private val cookieValidator: CookieValidator
) {

    fun login(username:String, password:String): HttpResponse<Any> {
        val user = repository.findByUsername(username) ?: return HttpResponse.unauthorized()
//        println("user in login service "+user.toString())
//        println(passwordEncryptService.passwordToHash(password))
//        println(password)
//        println(user.passwordHash)
        if(passwordEncryptService.passwordToHash(password)==user.passwordHash)
            return HttpResponse.redirect<Any?>(URI("/profile"))
                                .cookie(cookieTokenProvider.releaseCookie(user,"user"))
        else return HttpResponse.unauthorized()
    }



    fun profilePage(request: HttpRequest<*>): HttpResponse<String> {
        val token = request.cookies["AUTH-TOKEN"]

        if (token != null) {
            val user = cookieValidator.returnUserIfAuthTokenValid(token)
            if (user!=null) {
                return HttpResponse.ok("Token is valid, ${user.username}\n${user}")
            }
            return HttpResponse.unauthorized()
        }
        else{
            return HttpResponse.unauthorized()
        }

    }

}