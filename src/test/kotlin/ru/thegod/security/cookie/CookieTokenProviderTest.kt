package ru.thegod.security.cookie

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.thegod.providers.TestObjectsProvider
import ru.thegod.security.cookies.CryptImpl
import ru.thegod.security.cookies.service.CookieTokenProvider
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
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
        val user = TestObjectsProvider.USER_ME
        val cookie = cookieTokenProvider.releaseCookie(user,"user")
        Assertions.assertEquals(true, cookie.isHttpOnly)
        Assertions.assertEquals(true, cookie.isSecure)
        Assertions.assertEquals("AUTH-TOKEN", cookie.name)
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
        Assertions.assertEquals(input, plainText)
    }
}