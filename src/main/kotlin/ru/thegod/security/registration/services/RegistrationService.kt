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
class RegistrationService(private val userService: UserService
) {

    fun registerNewUser(username:String, password:String): Boolean{
        if (!userService.isUsernameValid(username))
            return false
        if (!userService.isPasswordValid(password))
            return false

        if(userService.findUserByUsername(username)!=null)
            return false

        userService.saveUser(User(username, password.md5()))

        return true
    }


}