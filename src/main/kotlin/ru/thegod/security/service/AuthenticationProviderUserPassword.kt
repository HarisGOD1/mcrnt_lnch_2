package ru.thegod.security.service

import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationFailureReason
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.provider.HttpRequestAuthenticationProvider
import jakarta.inject.Singleton

@Singleton
class AuthenticationProviderUserPassword<B>(private val loginService:LoginService) : HttpRequestAuthenticationProvider<B> {

    override fun authenticate(
        httpRequest: HttpRequest<B>?,
        authenticationRequest: AuthenticationRequest<String, String>
    ): AuthenticationResponse {
        println(authenticationRequest.identity)
        println(authenticationRequest.secret)
        val userFromDB = loginService.getUserByUsername(authenticationRequest.identity)
        println(userFromDB)
        return if(passwordEncryptService.passwordToHash(authenticationRequest.secret)==userFromDB.passwordHash)
            AuthenticationResponse.success(authenticationRequest.identity)
        else AuthenticationResponse.failure(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH)
//
    }
}

//@Singleton
//class AuthenticationProviderUserPassword<B> : HttpRequestAuthenticationProvider<B> {
//
//    override fun authenticate(
//        httpRequest: HttpRequest<B>?,
//        authenticationRequest: AuthenticationRequest<String, String>
//    ): AuthenticationResponse {
//        return if (authenticationRequest.identity == "sherlock" && authenticationRequest.secret == "password")
//            AuthenticationResponse.success(authenticationRequest.identity) else
//            AuthenticationResponse.failure(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH)
//    }
//}
