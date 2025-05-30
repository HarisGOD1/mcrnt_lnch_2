package ru.thegod.security.service

import io.micronaut.security.authentication.AuthenticationRequest
import jakarta.inject.Singleton
import ru.thegod.security.UserRepository

@Singleton
class AuthenticationService(private val repository: UserRepository) {
    fun checkCredentials(request: AuthenticationRequest<String,String>): Boolean {
        val user = repository.findByUsername(request.identity)
        if (user!=null)
            if (user.passwordHash == passwordEncryptService.passwordToHash(request.secret))
                return true
        println("CHECK -> FALSE")
        return false
    }
}