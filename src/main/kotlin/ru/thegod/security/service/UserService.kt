package ru.thegod.security.service

import jakarta.inject.Singleton
import ru.thegod.security.User
import ru.thegod.security.UserRepository

@Singleton
class UserService(private val repository: UserRepository) {
    fun getUser(username:String): User? {
        return repository.findByUsername(username)
    }
}