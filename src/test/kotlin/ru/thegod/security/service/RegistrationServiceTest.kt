package ru.thegod.security.service

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import ru.thegod.providers.TestObjectsProvider
import ru.thegod.security.user.UserRepository
import ru.thegod.security.cookies.service.CookieValidator
import ru.thegod.security.registration.RegistrationService

@MicronautTest
class RegistrationServiceTest {

    @Inject
    private lateinit var registerService: RegistrationService
    @Inject
    private lateinit var userRepository: UserRepository
    @Inject
    private lateinit var cookieValidator: CookieValidator


    @Test
    fun `register new user returns cookie and user saved test`(){
        val userPassword = "ppp"
        val user = TestObjectsProvider.getRandomUser(userPassword)
        val httpResp = registerService.registerNewUser(user.username,userPassword)


        val token = httpResp.cookies["AUTH-TOKEN"]

        val userFromDB = userRepository.findByUsername(user.username)
        assertNotNull(userFromDB)
        assertEquals(user,userFromDB)

        val userFromToken = cookieValidator.returnUserIfAuthTokenValid(token)
        assertNotNull(userFromToken)
        assertEquals(user.username,userFromToken!!.username)
    }


}