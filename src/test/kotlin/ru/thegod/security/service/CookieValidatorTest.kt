package ru.thegod.security.service

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import ru.thegod.security.cookie.CookieTokenProvider
import ru.thegod.security.cookie.CookieValidator
import ru.thegod.security.cookie.CryptImpl

@MicronautTest
class CookieValidatorTest {
    @Inject
    lateinit var cryptImpl: CryptImpl
    @Inject
    lateinit var cookieValidator: CookieValidator
    @Inject
    lateinit var cookieTokenProvider: CookieTokenProvider


    @Suppress("UNCHECKED_CAST")
    @Test
    fun `test real cookie is validated`(){
        val username = "somebody"
        val cookie = cookieTokenProvider.releaseCookie(username,"user")
        val usernameFromProvider = cookieValidator.returnUsernameIfAuthTokenValid(cookie)
        assertNotNull(usernameFromProvider)
        assertEquals(username,usernameFromProvider)

    }


}