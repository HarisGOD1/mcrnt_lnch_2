package ru.thegod.security.service

import jakarta.inject.Singleton
import ru.thegod.security.User
import ru.thegod.security.UserRepository
import ru.thegod.security.service.passwordEncryptService.md5

@Singleton
class UserService(private val repository: UserRepository) {

    fun registerNewUser(username:String, password:String): User?{
        if (!isUsernameValid(username)) return null
        if(repository.findByUsername(username)!=null) return null

        return repository.save(User(null,username,password.md5()))

    }
    fun changeUserPassword(username:String, passwordHash:String, password:String): User?{
        if (!isUsernameValid(username)) return null
        var user: User? = repository.findByUsername(username) ?: return null
        if (user!!.passwordHash==passwordHash) {
            user.passwordHash = password.md5()
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