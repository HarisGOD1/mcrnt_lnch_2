package ru.thegod.security.cookies

import java.util.*
import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.http.cookie.Cookie
import io.micronaut.http.cookie.SameSite
import jakarta.inject.Inject
import jakarta.inject.Singleton
import ru.thegod.security.User
import ru.thegod.security.UserRepository
import java.time.Clock
import java.time.Duration

// AES/GCM
// Set-Cookie: AUTH-TOKEN=eyJhbGciOiJIUzI1NiIsInR5cCI6...; Path=/; HttpOnly; Secure; SameSite=Strict
// AUTH-TOKEN=header.payload.signature -> Each part is Base64URL-encoded
// VCJ9.eyDB9.rN8xY
// Header: algorithm & token type (e.g., {"alg":"HS256","typ":"JWT"})       /_____  is the
// Payload: claims {"userId":123,"exp":170000000}                           \-----  JSON
// Signature: generated using the header, payload, and secret key

@Singleton
class CookieTokenProvider(private val userRepository: UserRepository) {

    private val objectMapper: ObjectMapper by lazy{
        ObjectMapper()
    }

    @Inject
    private lateinit var cryptImpl:CryptImpl

    // generates token-value aka
    fun generateToken(user: User, role: String):String{

        val header = mapOf("alg" to "AES/GCM/NoPadding","typ" to "JWT")
        val headerJSON = objectMapper.writeValueAsString(header)

        val payloadJSON=objectMapper.writeValueAsString(
            mapOf("username" to user.username,
                "role" to role,
                "born" to Clock.systemUTC().millis(),
                "security_hash" to user.securityHash())
        )

        val signature: String = cryptImpl.encrypt(headerJSON+"."+payloadJSON)

        val tokenValue = Base64.getEncoder().encodeToString(headerJSON.toByteArray())+"."+
                Base64.getEncoder().encodeToString(payloadJSON.toByteArray())+"."+
                Base64.getEncoder().encodeToString(signature.toByteArray())
        return tokenValue
    }
    //
    fun releaseCookie(user: User, role:String): Cookie{
//        val setCookieString:String = "thegodtoken="+tokenValue+"; Path=/; HttpOnly; Secure; SameSite=Strict"
        val cookie = Cookie.of("AUTH-TOKEN", generateToken(user,role))
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(Duration.ofHours(1))
            .sameSite(SameSite.Strict)

        return cookie
    }

    fun releaseCookie(user: User):Cookie {
        return releaseCookie(user, "user")
    }

    fun releaseExpiredCookie():Cookie{
        val cookie = Cookie.of("AUTH-TOKEN", "empty")
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(Duration.ofMillis(1))
            .sameSite(SameSite.Strict)
        return cookie
    }

}