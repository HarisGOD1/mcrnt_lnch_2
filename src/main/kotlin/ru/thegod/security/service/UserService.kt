package ru.thegod.security.service

import io.micronaut.http.HttpResponse
import jakarta.inject.Singleton
import ru.thegod.security.User
import ru.thegod.security.UserRepository
import ru.thegod.security.cookies.CookieTokenProvider
import ru.thegod.security.service.PasswordEncryptService.md5
import java.net.URI

@Singleton
class UserService(private val repository: UserRepository,
                  private val cookieTokenProvider: CookieTokenProvider) {

    fun registerNewUser(username:String, password:String): HttpResponse<Any?>{
        if (!isUsernameValid(username)) return HttpResponse.unauthorized()
        if(repository.findByUsername(username)!=null) return HttpResponse.unauthorized()


        val user = repository.save(User(null,username,password.md5()))
        val token = cookieTokenProvider.releaseCookie(user,"user")

        return HttpResponse.redirect<Any?>(URI("/profile"))
                            .cookie(token)
    }
    fun changeUserPassword(username:String, passwordHash:String, newPassword:String): User?{
        if (!isUsernameValid(username)) return null
        var user: User? = repository.findByUsername(username) ?: return null
        if (user!!.passwordHash==passwordHash) {
            user.passwordHash = newPassword.md5()
            return repository.update(user)
        }
        else{
            return null
        }
    }

    // [a-z][A-Z][0-9]_
    fun isUsernameValid(username: String):Boolean{
        val regex = Regex("^[a-zA-Z0-9_]+$")
        return regex.matches(username) and (2<username.length) and (username.length<30)
    }
}