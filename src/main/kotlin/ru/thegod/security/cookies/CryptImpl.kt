package ru.thegod.security.cookies

import io.micronaut.context.annotation.Value
import jakarta.inject.Singleton
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.spec.InvalidKeySpecException
import java.security.spec.KeySpec
import java.util.Base64
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

@Singleton
class CryptImpl(@Value("\${ru.thegod.secret-password}")
                private val secretPassword: String) {
    private val secret: SecretKey by lazy {
        getKeyFromPassword(secretPassword,"2c")
    }
    private val defaultIV: GCMParameterSpec by lazy{
        generateIv()
    }
    private val algorithm:String="AES/GCM/NoPadding"

    @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class)
    fun getKeyFromPassword(password: String, salt: String): SecretKey {
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val spec: KeySpec = PBEKeySpec(password.toCharArray(), salt.toByteArray(), 65536, 256)
        val secret: SecretKey = SecretKeySpec(
            factory.generateSecret(spec)
                .encoded, "AES"
        )
        return secret
    }

    fun generateIv(): GCMParameterSpec {
        val iv = ByteArray(12)
        SecureRandom().nextBytes(iv)
        return GCMParameterSpec(128, iv)
    }

    @Throws(
        NoSuchPaddingException::class,
        NoSuchAlgorithmException::class,
        InvalidAlgorithmParameterException::class,
        InvalidKeyException::class,
        BadPaddingException::class,
        IllegalBlockSizeException::class
    )
    fun encrypt(
        algorithm: String, input: String, key: SecretKey,
        iv: GCMParameterSpec
    ): String {
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.ENCRYPT_MODE, key, iv)
        val cipherText = cipher.doFinal(input.toByteArray())
        return Base64.getEncoder()
            .encodeToString(cipherText)
    }

    fun encrypt(input: String): String {
        return encrypt(algorithm,input,secret,defaultIV)
    }




    @Throws(
        NoSuchPaddingException::class,
        NoSuchAlgorithmException::class,
        InvalidAlgorithmParameterException::class,
        InvalidKeyException::class,
        BadPaddingException::class,
        IllegalBlockSizeException::class
    )
    fun decrypt(
        algorithm: String, cipherText: String?, key: SecretKey?,
        iv: GCMParameterSpec?
    ): String {
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.DECRYPT_MODE, key, iv)
        val plainText = cipher.doFinal(
            Base64.getDecoder()
                .decode(cipherText)
        )
        return String(plainText)
    }

    fun decrypt(cipherText: String?
    ): String {
        return decrypt(algorithm,cipherText,secret,defaultIV)
    }
}