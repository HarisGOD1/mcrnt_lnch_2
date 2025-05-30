package ru.thegod.security.cookie

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.util.*

/*
* RFC-cookie: RFC 6265    -->      https://datatracker.ietf.org/doc/html/rfc6265
*
*   Verification: The server verifies the JWT by:
*   |o| Checking the signature using the secret or public key.
*   |o| Validating the claims (e.g., checking if the token has expired, if the issuer is correct, etc.).
*
*
*   cookie is statelessness
*
 */
@Singleton
class CookieValidator {
    private val objectMapper: ObjectMapper by lazy{
        ObjectMapper()
    }
    @Inject
    private lateinit var cryptImpl:CryptImpl
    // examples https://datatracker.ietf.org/doc/html/rfc6265#section-3.1
    //Set-Cookie: id=a3fWa; Expires=Wed, 21 Oct 2015 07:28:00 GMT; Secure; HttpOnly
    fun returnUsernameIfAuthCookieValid(cookie:String):String?{
        println(cookie)
        val blocks = cookie.split(".")
        val headerJSON = String(Base64.getDecoder().decode(blocks.get(0)))
        val payloadJSON = String(Base64.getDecoder().decode(blocks.get(1)))
        val signatureCrypt = String(Base64.getDecoder().decode(blocks.get(2)))
        val payloadMap: Map<String, Any> = objectMapper.readValue(payloadJSON, Map::class.java) as Map<String, Any>


        val signature = cryptImpl.decrypt(signatureCrypt)
        if ((headerJSON+"."+payloadJSON)==signature)
            return payloadMap["username"].toString()
        else return null
    }
    fun getUsernameFromCookie(cookie:String):String{
        return ""
    }

    // Verification checks if a product or process is built according to the specified requirements, while
    // Validation checks if the product or process actually meets the intended needs and user expectations
    // so to check the cookie is right -> we need validate

    // Symmetric encryption algorithms like AES (e.g., AES-256-GCM or AES-256-CBC with HMAC)
    // are typically preferred for encrypting data

    // CBC model oracle padding attack - not so much about need to care


}