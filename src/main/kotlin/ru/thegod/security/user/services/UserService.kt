package ru.thegod.security.user.services

import jakarta.inject.Singleton
import ru.thegod.security.authentication.services.PasswordEncryptService.md5
import ru.thegod.security.user.User
import ru.thegod.security.user.UserRepository

@Singleton
class UserService(private val repository: UserRepository)
{

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