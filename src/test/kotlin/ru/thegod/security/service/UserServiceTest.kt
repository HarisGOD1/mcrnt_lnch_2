package ru.thegod.security.service

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.thegod.providers.TestObjectsProvider
import ru.thegod.security.UserRepository
import ru.thegod.security.cookie.CookieTokenProvider
import ru.thegod.security.cookie.CookieValidator

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
    fun `register new user returns cookie and user saved test`(){
        val userPassword = "ppp"
        val user = TestObjectsProvider.getRandomUser(userPassword)
        val httpResp = userService.registerNewUser(user.username,userPassword)


        val token = httpResp.cookies["AUTH-TOKEN"]

        val userFromDB = userRepository.findByUsername(user.username)
        assertNotNull(userFromDB)
        assertEquals(user,userFromDB)
        val userFromToken = cookieValidator.returnUserIfAuthTokenValid(token)
        assertNotNull(userFromToken)
        assertEquals(user.username,userFromToken!!.username)
    }

    @Test
    fun `existed user change password and token invalidates test`(){
        val userPassword = "ppp"
        val user = TestObjectsProvider.USER_ME
        userRepository.save(user)
        val newUserPassword = "pppp"
        val token = cookieTokenProvider.releaseCookie(user,"user")

        val httpResp = userService.changeUserPassword(user.username,user.passwordHash,newUserPassword)

        val userFromDB = userRepository.findByUsername(user.username)
        assertNotNull(userFromDB)
        assertNotEquals(user.passwordHash,userFromDB!!.passwordHash)
        assertNull(cookieValidator.returnUserIfAuthTokenValid(token))
    }


}