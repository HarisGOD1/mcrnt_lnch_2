package ru.thegod.security.service

import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.provider.HttpRequestReactiveAuthenticationProvider
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink

@Singleton
class AuthenticationProviderUserPassword<B> : HttpRequestReactiveAuthenticationProvider<B>{

    @Inject
    private lateinit var authenticationService: AuthenticationService

    override fun authenticate(
        httpRequest: HttpRequest<B>?,
        authenticationRequest: AuthenticationRequest<String, String>
    ): Publisher<AuthenticationResponse> {
        return Flux.create({ emitter: FluxSink<AuthenticationResponse> ->
            if (authenticationService.checkCredentials(authenticationRequest)) {
                println("CHECK -> TRUE")
                emitter.next(AuthenticationResponse.success(authenticationRequest.identity as String))
                emitter.complete()
            } else {
                emitter.error(AuthenticationResponse.exception())
            }
        }, FluxSink.OverflowStrategy.ERROR)
    }
}


//@Singleton
//class AuthenticationProviderUserPassword<B> : HttpRequestReactiveAuthenticationProvider<B> {
//
//    override fun authenticate(
//        httpRequest: HttpRequest<B>?,
//        authenticationRequest: AuthenticationRequest<String, String>
//    ): Publisher<AuthenticationResponse> {
//        return Flux.create({ emitter: FluxSink<AuthenticationResponse> ->
//            if (authenticationRequest.identity == "sherlock" && authenticationRequest.secret == "password") {
//                emitter.next(AuthenticationResponse.success(authenticationRequest.identity as String))
//                emitter.complete()
//            } else {
//                emitter.error(AuthenticationResponse.exception())
//            }
//        }, FluxSink.OverflowStrategy.ERROR)
//    }
//}