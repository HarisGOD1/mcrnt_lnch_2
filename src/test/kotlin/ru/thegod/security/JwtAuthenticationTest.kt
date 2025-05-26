package ru.thegod.security

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus.OK
import io.micronaut.http.HttpStatus.UNAUTHORIZED
import io.micronaut.http.MediaType.TEXT_PLAIN
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

import com.nimbusds.jwt.JWTParser
import com.nimbusds.jwt.SignedJWT
import com.nimbusds.jwt.PlainJWT
import io.micronaut.http.HttpResponse
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.render.BearerAccessRefreshToken
import org.junit.jupiter.api.Assertions.assertNotNull
@MicronautTest
class JwtAuthenticationTest(@Client("/") val client: HttpClient) {

    @Test
    fun accessingASecuredUrlWithoutAuthenticatingReturnsUnauthorized() {
        val e = assertThrows(HttpClientResponseException::class.java) {
            client.toBlocking().exchange<Any, Any>(HttpRequest.GET<Any>("/").accept(TEXT_PLAIN))
        }
        assertEquals(UNAUTHORIZED, e.status)
    }

    @Test
    fun uponSuccessfulAuthenticationAJsonWebTokenIsIssuedToTheUser() {
        val creds = UsernamePasswordCredentials("sherlock", "password")
        val request: HttpRequest<*> = HttpRequest.POST("/login", creds)
        val rsp: HttpResponse<BearerAccessRefreshToken> =
            client.toBlocking().exchange(request, BearerAccessRefreshToken::class.java)
        assertEquals(OK, rsp.status)

        val bearerAccessRefreshToken: BearerAccessRefreshToken = rsp.body()
        assertEquals("sherlock", bearerAccessRefreshToken.username)
        assertNotNull(bearerAccessRefreshToken.accessToken)
        println(bearerAccessRefreshToken.accessToken)
        println(bearerAccessRefreshToken)
        println(JWTParser.parse(bearerAccessRefreshToken.accessToken))
        println()
        assertTrue(JWTParser.parse(bearerAccessRefreshToken.accessToken) is SignedJWT)

        val accessToken: String = bearerAccessRefreshToken.accessToken
        val requestWithAuthorization = HttpRequest.GET<Any>("/")
            .accept(TEXT_PLAIN)
            .bearerAuth(accessToken)
        val response: HttpResponse<String> = client.toBlocking().exchange(requestWithAuthorization, String::class.java)

        assertEquals(OK, rsp.status)
        assertEquals("sherlock", response.body())
    }
}