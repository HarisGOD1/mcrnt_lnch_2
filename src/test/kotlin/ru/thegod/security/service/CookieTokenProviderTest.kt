package ru.thegod.security.service

import io.micronaut.http.cookie.Cookie
import io.micronaut.http.cookie.SameSite
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.thegod.providers.TestObjectsProvider
import ru.thegod.security.cookie.CookieTokenProvider
import ru.thegod.security.cookie.CryptImpl
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.time.Duration
import javax.crypto.BadPaddingException
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

@MicronautTest
class CookieTokenProviderTest {

    @Inject
    lateinit var cryptImpl: CryptImpl
    @Inject
    lateinit var cookieTokenProvider: CookieTokenProvider

    @Test
    fun `test provider`(){
        val user = TestObjectsProvider.userMe
        val cookie = cookieTokenProvider.releaseCookie(user,"role")
//        val key = cryptImpl.getKeyFromPassword("stri","sal")

//        val cookie = Cookie.of("AUTH-TOKEN", tokenValue)
//            .httpOnly(true)
//            .secure(true)
//            .path("/")
//            .maxAge(Duration.ofHours(1))
//            .sameSite(SameSite.Strict)
        assertEquals(true,cookie.isHttpOnly)
        assertEquals(true,cookie.isSecure)
        assertEquals("AUTH-TOKEN",cookie.name)

//        var text_encrypted = cookieTokenProvider.encrypt()

    }

    @Test
    @Throws(
        NoSuchAlgorithmException::class,
        IllegalBlockSizeException::class,
        InvalidKeyException::class,
        BadPaddingException::class,
        InvalidAlgorithmParameterException::class,
        NoSuchPaddingException::class
    )
    fun givenString_whenEncrypt_thenSuccess() {
        val input = "baeldung is wrote this code for me"
        val key: SecretKey = cryptImpl.getKeyFromPassword("stri","sal")
        val gcmParameterSpec: GCMParameterSpec = cryptImpl.generateIv()
        val algorithm = "AES/GCM/NoPadding"
        val cipherText: String = cryptImpl.encrypt(algorithm, input, key, gcmParameterSpec)
        val plainText: String = cryptImpl.decrypt(algorithm, cipherText, key, gcmParameterSpec)
        assertEquals(input, plainText)
    }
}