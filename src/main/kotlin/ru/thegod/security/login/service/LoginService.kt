package ru.thegod.security.login.service

import io.micronaut.http.cookie.Cookie
import jakarta.inject.Singleton
import ru.thegod.security.user.UserRepository
import ru.thegod.security.authentication.services.PasswordEncryptService
import ru.thegod.security.cookies.storage.ExpiredTokenStorage
import ru.thegod.security.cookies.service.CookieTokenProvider
import ru.thegod.security.cookies.service.CookieValidator
import java.time.Clock

@Singleton
class LoginService(private val repository: UserRepository,
                   private val cookieTokenProvider: CookieTokenProvider,
                   private val cookieValidator: CookieValidator,
                   private val expiredTokenStorage: ExpiredTokenStorage
) {

    // return user if authorized or returns null
    fun loginReturnToken(username:String, password:String): Cookie? {
        val user = repository.findByUsername(username) ?: return null
        if(PasswordEncryptService.passwordToHash(password)==user.passwordHash)
            return cookieTokenProvider.releaseCookie(user,"user")
        else return null
    }


//    from RFC 6265
//    Finally, to remove a cookie, the server returns a Set-Cookie header
//    with an expiration date in the past.  The server will be successful
//    in removing the cookie only if the Path and the Domain attribute in
//    the Set-Cookie header match the values used when the cookie was
//    created.
    fun performLogout(token: Cookie): Cookie?{
        val user = cookieValidator.returnUserIfAuthTokenValid(token)
        if (user==null) return null

        return replaceCookie(token)
    }
    fun performLogoutAll(token: Cookie): Cookie?{
//        val token = request.cookies["AUTH-TOKEN"]

        val user = cookieValidator.returnUserIfAuthTokenValid(token)
        if(user==null) return null

        return makeAllCookieDied(user.username)

    }

    fun replaceCookie(token: Cookie): Cookie {
        expiredTokenStorage.putSingleExpiredToken(token.value)
        return cookieTokenProvider.releaseExpiredCookie()
    }
    fun makeAllCookieDied(username: String): Cookie {
        expiredTokenStorage.putAllExpiredTime(username, Clock.systemUTC().millis())
        return cookieTokenProvider.releaseExpiredCookie()
    }





}