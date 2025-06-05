package ru.thegod.security.user.services

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.thegod.providers.TestObjectsProvider
import ru.thegod.security.cookies.service.CookieTokenProvider
import ru.thegod.security.cookies.service.CookieValidator
import ru.thegod.security.user.repositories.UserRepository

@MicronautTest
class UserServiceTest {
    @Inject
    private lateinit var userService: UserService
    @Inject
    private lateinit var userRepository: UserRepository
    @Inject
    private lateinit var cookieValidator: CookieValidator
    @Inject
    private lateinit var cookieTokenProvider: CookieTokenProvider

    @Test
    fun `existed user change password and token invalidates test`(){
        val userPassword = "pppppp"
        val user = TestObjectsProvider.USER_ME
        userRepository.save(user)
        val newUserPassword = "ppppppp"
        val token = cookieTokenProvider.releaseCookie(user,"user")

        val httpResp = userService.changeUserPassword(user.username,user.passwordHash,newUserPassword)

        val userFromDB = userRepository.findByUsername(user.username)
        Assertions.assertNotNull(userFromDB)
        Assertions.assertNotEquals(user.passwordHash, userFromDB!!.passwordHash)
        Assertions.assertNull(cookieValidator.returnUserIfAuthTokenValid(token))
    }


}