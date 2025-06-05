package ru.thegod.security.cookies.service

import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.http.cookie.Cookie
import jakarta.inject.Inject
import jakarta.inject.Singleton
import ru.thegod.security.user.models.User
import ru.thegod.security.cookies.CryptImpl
import ru.thegod.security.cookies.redis.ExpiredTokenStorageService
import ru.thegod.security.user.services.UserService
import java.time.Clock
import java.util.Base64

/*
* RFC-cookie: RFC 6265    -->      https://datatracker.ietf.org/doc/html/rfc6265
*
*   Verification: The server verifies the JWT by:
*   |o| Checking the signature using the secret or public key.
*   |o| Validating the claims (e.g., checking if the token has expired, if the issuer is correct, etc.).
*
*
*   cookie is statelessness
*   all info contained in token itself
*
*   but the logout info then needs to be saved in server side
*   to save all expired tokens will be use Redis
*       - redis.logout_table.save(username,token-value)
*       - httpRequest.setCookie(token-expired)
*   and token itself will contain user-state-hash
*   which must be up-to-date or the token is expired,
*   due to changes in user state
*       - token_value.put(user.getSecurityHash())
*       user.getSecurityHash() = hash(username, passwordHash, main-email & etc)
*       if user change his password, then password hash will be changed
*       the user, that changed password will receive new token
*       and the other tokens will be not valid in later usage
 */
@Singleton
class CookieValidator (private val userService: UserService,
                       private val expiredTokenStorageService: ExpiredTokenStorageService
){
    private val objectMapper: ObjectMapper by lazy{
        ObjectMapper()
    }
    @Inject
    private lateinit var cryptImpl: CryptImpl
    // examples https://datatracker.ietf.org/doc/html/rfc6265#section-3.1
    //Set-Cookie: id=a3fWa; Expires=Wed, 21 Oct 2015 07:28:00 GMT; Secure; HttpOnly

    fun returnUserIfAuthTokenValid(token: Cookie): User?{
        val (headerJSON,payloadJSON,signature) = extractPayload(token)
        // if user's token was damaged, then his token's signature
        // will be inconsistent with his payload
        // This is checking for consistency
        if ((headerJSON+"."+payloadJSON)!=signature) return null

        val payloadMap = objectMapper.readValue(payloadJSON, Map::class.java) as? Map<String, Any>
        if(payloadMap==null) return null

        if(!expiredTokenStorageService.getSingleExpiredToken(token.value).isEmpty) return null

        val bornTime = getBornTimeFromPayloadMap(payloadMap)
        val username = getUsernameFromPayloadMap(payloadMap)
        val securityHash = getSecurityHashFromPayloadMap(payloadMap)


        // user may perform invalidation of all his released tokens
        // then we need to check for those tokens, using storage
        val lastAllExpired=expiredTokenStorageService.getAllExpiredTime(username)
        if(!lastAllExpired.isEmpty)
            if(bornTime<lastAllExpired.get().toLong())
                return null

        // check if token out of default time long
        if(Clock.systemUTC().millis()> (bornTime+3600000))
            return null

        val user = userService.findUserByUsername(username) ?: return null
        // security check needed in case if token user
        // changed password, then any other tokens will be invalid
        if (securityHash != user.securityHash()) return null


        return user

    }



    // extracting parts of token and decode them into JSON strings
    private fun extractPayload(token: Cookie): Triple<String, String, String> {
        val blocks = token.value.split(".")

        val headerJSON = String(Base64.getDecoder().decode(blocks.get(0)))
        val payloadJSON = String(Base64.getDecoder().decode(blocks.get(1)))
        val signatureCrypt = String(Base64.getDecoder().decode(blocks.get(2)))
        val signature = cryptImpl.decrypt(signatureCrypt)
        return Triple(headerJSON,payloadJSON,signature)
    }

    fun getBornTimeFromPayloadMap(payloadMap:Map<String,Any>):Long{
        return payloadMap["born"].toString().toLong()
    }

    fun getUsernameFromPayloadMap(payloadMap:Map<String,Any>):String{
        return payloadMap["username"].toString()
    }
    fun getSecurityHashFromPayloadMap(payloadMap:Map<String,Any>):String{
        return payloadMap["security_hash"].toString()
    }



    // Verification checks if a product or process is built according to the specified requirements, while
    // Validation checks if the product or process actually meets the intended needs and user expectations
    // so to check the cookie is right -> we need validate

    // Symmetric encryption algorithms like AES (e.g., AES-256-GCM or AES-256-CBC with HMAC)
    // are typically preferred for encrypting data

    // CBC model oracle padding attack - not so much about need to care


}