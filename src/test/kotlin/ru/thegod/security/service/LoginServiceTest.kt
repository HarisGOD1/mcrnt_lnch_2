package ru.thegod.security.service

import io.micronaut.http.HttpRequest
import io.micronaut.http.MediaType
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.thegod.gitr.dto.GitrEntityRequestDTO
import ru.thegod.providers.TestObjectsProvider
import ru.thegod.security.UserRepository
import ru.thegod.security.cookie.CookieValidator
import java.net.URI

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
        val httpResp = loginService.login(user.username,userPassword) // TO-DO: make it work with register endpoint
        val token = httpResp.cookies["AUTH-TOKEN"]
        assertNotNull(token.value)
        val userFromToken = cookieValidator.returnUserIfAuthTokenValid(token)
        assertNotNull(userFromToken)
        assertEquals(user.username,userFromToken!!.username)
    }

    @Test
    fun `test single logout returns expired token`(){
        val userPassword = "ppp"
        val user = TestObjectsProvider.getRandomUser(userPassword)
        userRepository.save(user)
        val httpResp = loginService.login(user.username,userPassword) // TO-DO: make it work with register endpoint
        val token = httpResp.cookies["AUTH-TOKEN"]
        assertNotNull(token.value)
        assertNotNull(cookieValidator.returnUserIfAuthTokenValid(token))

        loginService.performLogout(token)

        assertNull(cookieValidator.returnUserIfAuthTokenValid(token))

    }

    @Test
    fun `test logout all returns expired token and new token is up to date`(){
        val userPassword = "ppp"
        val user = TestObjectsProvider.getRandomUser(userPassword)
        userRepository.save(user)
        val httpRespFirst = loginService.login(user.username,userPassword)
        Thread.sleep(500) // To be sure, that second token isn't equal to first
                               // (Tokens equal, if released at same millisecond time)
        val httpRespSecond = loginService.login(user.username,userPassword)// TO-DO: make it work with register endpoint
        val tokenFirst = httpRespFirst.cookies["AUTH-TOKEN"]
        val tokenSecond = httpRespSecond.cookies["AUTH-TOKEN"]

        assertNotNull(tokenFirst.value)
        assertNotNull(cookieValidator.returnUserIfAuthTokenValid(tokenFirst))

        loginService.performLogoutAll(tokenFirst)
        assertNull(cookieValidator.returnUserIfAuthTokenValid(tokenSecond))

        Thread.sleep(500)
        val httpRespThird = loginService.login(user.username,userPassword)// TO-DO: make it work with register endpoint
        val tokenThird = httpRespThird.cookies["AUTH-TOKEN"]
        assertNotNull(tokenThird.value)
        assertNotNull(cookieValidator.returnUserIfAuthTokenValid(tokenThird))


    }




}