package ru.thegod.security.login

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.thegod.providers.TestObjectsProvider
import ru.thegod.security.cookies.service.CookieValidator
import ru.thegod.security.login.service.LoginService
import ru.thegod.security.user.repositories.UserRepository

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
        Assertions.assertNotNull(token!!.value)
        val userFromToken = cookieValidator.returnUserIfAuthTokenValid(token)
        Assertions.assertNotNull(userFromToken)
        Assertions.assertEquals(user.username, userFromToken!!.username)
    }

    @Test
    fun `test single logout returns expired token`(){
        val userPassword = "ppp"
        val user = TestObjectsProvider.getRandomUser(userPassword)
        userRepository.save(user)
        val token = loginService.loginReturnToken(user.username,userPassword)// TO-DO: make it work with register endpoint
        Assertions.assertNotNull(token)
        Assertions.assertNotNull(token!!.value)
        Assertions.assertNotNull(cookieValidator.returnUserIfAuthTokenValid(token))

        loginService.performLogout(token)

        Assertions.assertNull(cookieValidator.returnUserIfAuthTokenValid(token))

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

        Assertions.assertNotNull(tokenFirst)
        Assertions.assertNotNull(tokenFirst!!.value)
        Assertions.assertNotNull(tokenSecond)
        Assertions.assertNotNull(tokenSecond!!.value)

        Assertions.assertNotNull(cookieValidator.returnUserIfAuthTokenValid(tokenFirst))

        loginService.performLogoutAll(tokenFirst)
        Assertions.assertNull(cookieValidator.returnUserIfAuthTokenValid(tokenSecond))

        Thread.sleep(500)
        val tokenThird = loginService.loginReturnToken(user.username,userPassword)// TO-DO: make it work with register endpoint

        Assertions.assertNotNull(tokenThird)
        Assertions.assertNotNull(tokenThird!!.value)
        Assertions.assertNotNull(cookieValidator.returnUserIfAuthTokenValid(tokenThird))


    }




}