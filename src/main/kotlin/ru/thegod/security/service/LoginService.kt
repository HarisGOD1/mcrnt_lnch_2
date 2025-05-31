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
import ru.thegod.security.UserRepository
import ru.thegod.security.cookie.CookieTokenProvider
import ru.thegod.security.cookie.CookieValidator
import java.net.URI

@Singleton
class LoginService(private val repository: UserRepository,
                   private val cookieTokenProvider: CookieTokenProvider,
                   private val cookieValidator: CookieValidator
) {

    fun login(username:String, password:String): HttpResponse<Any> {
        val user = repository.findByUsername(username) ?: return HttpResponse.unauthorized()

        if(passwordEncryptService.passwordToHash(password)==user.passwordHash)
            return HttpResponse.redirect<Any?>(URI("/profile"))
                                .cookie(cookieTokenProvider.releaseCookie(username,"user"))
        else return HttpResponse.unauthorized()
    }



    fun profilePage(request: HttpRequest<*>): HttpResponse<String> {
        val token = request.cookies["AUTH-TOKEN"]

        return if (token != null) {
            val username = cookieValidator.returnUsernameIfAuthTokenValid(token)
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