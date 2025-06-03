package ru.thegod.security.authentication.services

import java.security.MessageDigest

object PasswordEncryptService {
    fun passwordToHash(password: String):String{
        return password.md5()
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(this.toByteArray())
        return digest.toHexString()
    }

}