package ru.thegod.security.service

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.thegod.providers.TestObjectsProvider
import ru.thegod.security.user.UserRepository
import ru.thegod.security.cookies.service.CookieTokenProvider
import ru.thegod.security.cookies.service.CookieValidator
import ru.thegod.security.cookies.CryptImpl
import ru.thegod.security.authentication.services.PasswordEncryptService.md5
// MAKING TRANSACTION FALSE DOESNT CLEAR THE REPOSITORIES
@MicronautTest
class CookieValidatorTest {
    @Inject
    lateinit var cryptImpl: CryptImpl
    @Inject
    lateinit var cookieValidator: CookieValidator
    @Inject
    lateinit var cookieTokenProvider: CookieTokenProvider
    @Inject
    lateinit var userRepository: UserRepository


    @Suppress("UNCHECKED_CAST")
    @Test
    fun `test real cookie is validated`(){
        val user = userRepository.save(TestObjectsProvider.USER_ME)
        assertNotNull(user)
        val cookie = cookieTokenProvider.releaseCookie(user,"user")
//        println(userRepository.findAll())
        val userFromToken = cookieValidator.returnUserIfAuthTokenValid(cookie)
        assertNotNull(userFromToken)
        assertEquals(user.username,userFromToken!!.username)

    }

    @Test
    fun `test cookie securityHash invalidated`(){
//        println(userRepository.findAll())
        var user = userRepository.save(TestObjectsProvider.USER_ME)
        assertNotNull(user)
        val cookie = cookieTokenProvider.releaseCookie(user,"user")
        user.passwordHash="anotherpassword".md5()
        userRepository.update(user)


        val userFromToken = cookieValidator.returnUserIfAuthTokenValid(cookie)
        assertNull(userFromToken)

    }



}