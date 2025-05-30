package ru.thegod.security.cookie

import java.util.*
import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.http.cookie.Cookie
import io.micronaut.http.cookie.SameSite
import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.time.Duration

// AES/GCM
// Set-Cookie: AUTH-TOKEN=eyJhbGciOiJIUzI1NiIsInR5cCI6...; Path=/; HttpOnly; Secure; SameSite=Strict
// AUTH-TOKEN=header.payload.signature -> Each part is Base64URL-encoded
// VCJ9.eyDB9.rN8xY
// Header: algorithm & token type (e.g., {"alg":"HS256","typ":"JWT"})       /_____  is the
// Payload: claims {"userId":123,"exp":170000000}                           \-----  JSON
// Signature: generated using the header, payload, and secret key

@Singleton
class CookieTokenProvider {

    private val objectMapper: ObjectMapper by lazy{
        ObjectMapper()
    }

    @Inject
    private lateinit var cryptImpl:CryptImpl


    //
    fun releaseCookie(username:String,role:String): Cookie{
        val header = mapOf("alg" to "HS256","typ" to "JWT")
        val headerJSON = objectMapper.writeValueAsString(header)
        val payloadJSON=objectMapper.writeValueAsString(
            mapOf("username" to username,
                  "role" to role))
        val signature: String = cryptImpl.encrypt(headerJSON+"."+payloadJSON)

        val tokenValue = Base64.getEncoder().encodeToString(headerJSON.toByteArray())+"."+
                Base64.getEncoder().encodeToString(payloadJSON.toByteArray())+"."+
                Base64.getEncoder().encodeToString(signature.toByteArray())

//        val setCookieString:String = "thegodtoken="+tokenValue+"; Path=/; HttpOnly; Secure; SameSite=Strict"

        val cookie = Cookie.of("AUTH-TOKEN", tokenValue)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(Duration.ofHours(1))
            .sameSite(SameSite.Strict)

        return cookie

    }

}