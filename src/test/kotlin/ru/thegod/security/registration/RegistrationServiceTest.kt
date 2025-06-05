package ru.thegod.security.registration

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.thegod.gitr.core.GitrRepository
import ru.thegod.providers.TestObjectsProvider
import ru.thegod.security.cookies.service.CookieValidator
import ru.thegod.security.registration.services.RegistrationService
import ru.thegod.security.user.repositories.UserRepository
import ru.thegod.security.user.roles.UserRole

@MicronautTest(transactional = false)
class RegistrationServiceTest {

    @Inject
    private lateinit var registerService: RegistrationService
    @Inject
    private lateinit var userRepository: UserRepository
    @Inject
    private lateinit var gitrRepository: GitrRepository
    @BeforeEach
    fun dropTablesBeforeEach(){
        gitrRepository.deleteAll()
        userRepository.deleteAll()
//        println("im stupid")
    }

    @Test
    fun `register new user and user saved test`(){
        val userPassword = "pppppp"
        val user = TestObjectsProvider.USER_ME
        registerService.registerNewUser(user.username,userPassword)


        println(userRepository.findAll())
        val userFromDB = userRepository.findByUsername(user.username)
        Assertions.assertNotNull(userFromDB)
        Assertions.assertEquals(user.username, userFromDB!!.username)

    }


}
