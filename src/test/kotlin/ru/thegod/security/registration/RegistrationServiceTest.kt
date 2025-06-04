package ru.thegod.security.registration

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.thegod.providers.TestObjectsProvider
import ru.thegod.security.cookies.service.CookieValidator
import ru.thegod.security.registration.services.RegistrationService
import ru.thegod.security.user.repositories.UserRepository

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
        Assertions.assertNotNull(userFromDB)
        Assertions.assertEquals(user, userFromDB)

        val userFromToken = cookieValidator.returnUserIfAuthTokenValid(token)
        Assertions.assertNotNull(userFromToken)
        Assertions.assertEquals(user.username, userFromToken!!.username)
    }


}