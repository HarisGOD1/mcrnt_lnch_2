package ru.thegod.security.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.thegod.security.cookie.CryptImpl
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.BadPaddingException
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec


class cookieTokenProviderTest {

    @Test
    fun `test provider`(){
        val key = CryptImpl.getKeyFromPassword("stri","sal")
        println(key)

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
        val key: SecretKey = CryptImpl.getKeyFromPassword("stri","sal")
        val gcmParameterSpec: GCMParameterSpec = CryptImpl.generateIv()
        val algorithm = "AES/GCM/NoPadding"
        val cipherText: String = CryptImpl.encrypt(algorithm, input, key, gcmParameterSpec)
        val plainText: String = CryptImpl.decrypt(algorithm, cipherText, key, gcmParameterSpec)
        println(input)
        println(cipherText)
        println(plainText)
        assertEquals(input, plainText)
    }
}