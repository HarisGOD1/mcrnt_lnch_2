package ru.thegod.security.service

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.cookie.Cookie
import jakarta.inject.Singleton
import ru.thegod.security.UserRepository
import ru.thegod.security.cookies.CookieTokenProvider
import ru.thegod.security.cookies.CookieValidator
import ru.thegod.security.cookies.ExpiredTokenStorage
import java.net.URI
import java.time.Clock

@Singleton
class LoginService(private val repository: UserRepository,
                   private val cookieTokenProvider: CookieTokenProvider,
                   private val cookieValidator: CookieValidator,
                   private val expiredTokenStorage: ExpiredTokenStorage
) {

    fun login(username:String, password:String): HttpResponse<Any> {
        val user = repository.findByUsername(username) ?: return HttpResponse.unauthorized()
        if(PasswordEncryptService.passwordToHash(password)==user.passwordHash)
            return HttpResponse.redirect<Any?>(URI("/profile"))
                                .cookie(cookieTokenProvider.releaseCookie(user,"user"))
        else return HttpResponse.unauthorized()
    }


//    from RFC 6265
//    Finally, to remove a cookie, the server returns a Set-Cookie header
//    with an expiration date in the past.  The server will be successful
//    in removing the cookie only if the Path and the Domain attribute in
//    the Set-Cookie header match the values used when the cookie was
//    created.
    fun performLogout(token: Cookie): HttpResponse<Any?>{
        val user = cookieValidator.returnUserIfAuthTokenValid(token)
        if (user==null) return HttpResponse.unauthorized()

        return HttpResponse.redirect<Any?>(URI("/logoutSuccess"))
            .cookie(replaceCookie(token))
    }

    fun replaceCookie(token: Cookie):Cookie{
        expiredTokenStorage.putSingleExpiredToken(token.value)
        return cookieTokenProvider.releaseExpiredCookie()
    }

    fun performLogoutAll(token: Cookie): HttpResponse<Any?>{
//        val token = request.cookies["AUTH-TOKEN"]

        val user = cookieValidator.returnUserIfAuthTokenValid(token)
        if(user==null) return HttpResponse.unauthorized()


        expiredTokenStorage.putAllExpiredTime(user.username, Clock.systemUTC().millis())
        return HttpResponse.redirect<Any?>(URI("/logoutSuccess"))
            .cookie(cookieTokenProvider.releaseExpiredCookie())

    }



}