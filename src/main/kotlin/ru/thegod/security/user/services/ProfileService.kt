package ru.thegod.security.user.services

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import jakarta.inject.Singleton
import ru.thegod.security.cookies.service.CookieValidator

@Singleton
class ProfileService(private val cookieValidator: CookieValidator) {


    fun profilePage(request: HttpRequest<*>): HttpResponse<String> {
        val token = request.cookies["AUTH-TOKEN"]
//        println(token)
        if (token != null) {
            val user = cookieValidator.returnUserIfAuthTokenValid(token)
//            println(user)
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