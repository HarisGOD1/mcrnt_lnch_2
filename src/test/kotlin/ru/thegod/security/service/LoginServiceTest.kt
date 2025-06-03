package ru.thegod.security.service

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.thegod.providers.TestObjectsProvider
import ru.thegod.security.user.UserRepository
import ru.thegod.security.cookies.service.CookieValidator
import ru.thegod.security.login.service.LoginService

@MicronautTest
class LoginServiceTest {

    @Inject
    lateinit var userRepository: UserRepository
    @Inject
    lateinit var loginService: LoginService
    @Inject
    lateinit var cookieValidator: CookieValidator


    @Test
    fun `test login as existed user returns cookie in Set-Cookie header`(){
        val userPassword = "ppp"
        val user = TestObjectsProvider.getRandomUser(userPassword)
        userRepository.save(user)
        val token = loginService.loginReturnToken(user.username,userPassword) // TO-DO: make it work with register endpoint
        assertNotNull(token!!.value)
        val userFromToken = cookieValidator.returnUserIfAuthTokenValid(token)
        assertNotNull(userFromToken)
        assertEquals(user.username,userFromToken!!.username)
    }

    @Test
    fun `test single logout returns expired token`(){
        val userPassword = "ppp"
        val user = TestObjectsProvider.getRandomUser(userPassword)
        userRepository.save(user)
        val token = loginService.loginReturnToken(user.username,userPassword)// TO-DO: make it work with register endpoint
        assertNotNull(token)
        assertNotNull(token!!.value)
        assertNotNull(cookieValidator.returnUserIfAuthTokenValid(token))

        loginService.performLogout(token)

        assertNull(cookieValidator.returnUserIfAuthTokenValid(token))

    }

    @Test
    fun `test logout all returns expired token and new token is up to date`(){
        val userPassword = "ppp"
        val user = TestObjectsProvider.getRandomUser(userPassword)
        userRepository.save(user)
        val tokenFirst = loginService.loginReturnToken(user.username,userPassword)
        Thread.sleep(500) // To be sure, that second token isn't equal to first
                               // (Tokens equal, if released at same millisecond time)
        val tokenSecond = loginService.loginReturnToken(user.username,userPassword)// TO-DO: make it work with register endpoint

        assertNotNull(tokenFirst)
        assertNotNull(tokenFirst!!.value)
        assertNotNull(tokenSecond)
        assertNotNull(tokenSecond!!.value)

        assertNotNull(cookieValidator.returnUserIfAuthTokenValid(tokenFirst))

        loginService.performLogoutAll(tokenFirst)
        assertNull(cookieValidator.returnUserIfAuthTokenValid(tokenSecond))

        Thread.sleep(500)
        val tokenThird = loginService.loginReturnToken(user.username,userPassword)// TO-DO: make it work with register endpoint

        assertNotNull(tokenThird)
        assertNotNull(tokenThird!!.value)
        assertNotNull(cookieValidator.returnUserIfAuthTokenValid(tokenThird))


    }




}