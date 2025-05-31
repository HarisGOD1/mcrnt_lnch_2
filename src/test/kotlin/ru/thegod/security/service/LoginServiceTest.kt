package ru.thegod.security.service

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import ru.thegod.providers.TestObjectsProvider
import ru.thegod.security.UserRepository
import ru.thegod.security.cookie.CookieValidator

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
        val user = TestObjectsProvider.getRandomUser()
        userRepository.save(user)

        val httpResp = loginService.login(user.username,user.passwordHash) // TO-DO: make it work with register endpoint
        val token = httpResp.cookies["AUTH-TOKEN"]
        assertNotNull(token.value)
        val usernameFromService = cookieValidator.returnUsernameIfAuthTokenValid(token)

        assertEquals(user.username,usernameFromService)


    }
}