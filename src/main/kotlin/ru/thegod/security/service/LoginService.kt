package ru.thegod.security.service

import jakarta.inject.Singleton
import ru.thegod.security.User

@Singleton
class LoginService(private val repository: UserRepository) {
    fun getUserByUsername(username:String): User {
        return repository.findByUsername(username)!!
    }
}