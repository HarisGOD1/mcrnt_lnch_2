package ru.thegod.security.service

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import ru.thegod.providers.TestObjectsProvider
import ru.thegod.security.UserRepository
import ru.thegod.security.cookie.CookieValidator
import ru.thegod.security.service.passwordEncryptService.md5

@MicronautTest
class LoginServiceTest {

    @Inject
    lateinit var userRepository: UserRepository
    @Inject
    lateinit var loginService: LoginService
    @Inject
    lateinit var cookieValidator: CookieValidator

    @Test
    fun `login as existed user returns cookie in Set-Cookie header`(){
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
}