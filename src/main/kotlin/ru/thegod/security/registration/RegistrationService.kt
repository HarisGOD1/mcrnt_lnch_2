package ru.thegod.security.registration

import io.micronaut.http.HttpResponse
import jakarta.inject.Singleton
import ru.thegod.security.authentication.services.PasswordEncryptService.md5
import ru.thegod.security.user.services.UserService
import ru.thegod.security.user.User
import ru.thegod.security.user.UserRepository
import java.net.URI

@Singleton
class RegistrationService(private val userService: UserService,
                          private val userRepository: UserRepository
) {

    fun registerNewUser(username:String, password:String): HttpResponse<Any?> {
        if (!userService.isUsernameValid(username)) return HttpResponse.unauthorized()
        if(userRepository.findByUsername(username)!=null) return HttpResponse.unauthorized()


        val user = userRepository.save(User(null, username, password.md5()))
//        val token = cookieTokenProvider.releaseCookie(user,"user")

        return HttpResponse.redirect<Any?>(URI("/login"))
//            .cookie(token)
    }


}