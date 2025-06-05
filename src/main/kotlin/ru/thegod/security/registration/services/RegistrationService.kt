package ru.thegod.security.registration.services

import io.micronaut.http.HttpResponse
import jakarta.inject.Singleton
import ru.thegod.security.authentication.services.PasswordEncryptService.md5
import ru.thegod.security.user.models.User
import ru.thegod.security.user.repositories.UserRepository
import ru.thegod.security.user.roles.UserRole
import ru.thegod.security.user.services.UserService
import java.net.URI

@Singleton
class RegistrationService(private val userService: UserService,
                          private val userRepository: UserRepository
) {

    fun registerNewUser(username:String, password:String): HttpResponse<Any?> {
        if (!userService.isUsernameValid(username))
            return HttpResponse.badRequest()


        if(userRepository.findByUsername(username)!=null)
            return HttpResponse.badRequest()


        val user = userRepository.save(User(username, password.md5()))
//        val token = cookieTokenProvider.releaseCookie(user,"user")

        return HttpResponse.redirect<Any?>(URI("/login"))
//            .cookie(token)
    }


}