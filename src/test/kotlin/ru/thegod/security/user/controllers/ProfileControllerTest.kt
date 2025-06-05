package ru.thegod.security.user.controllers

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.thegod.providers.TestObjectsProvider
import ru.thegod.security.cookies.service.CookieTokenProvider
import ru.thegod.security.user.repositories.UserRepository
import ru.thegod.security.user.services.ProfileService

@MicronautTest(transactional = false)
class ProfileControllerTest() {

    @Inject
    lateinit var userRepository: UserRepository
    @Inject
    lateinit var tokenProvider: CookieTokenProvider
    @Inject
    lateinit var profileService: ProfileService
    @Inject
    @Client("/")
    lateinit var client: HttpClient


    @Test
    fun `test profile returns profile info when token valid`(){
        val user = TestObjectsProvider.USER_ME
        userRepository.save(user)
        val token = tokenProvider.releaseCookie(user)
        val httpReq: HttpRequest<Any?> = HttpRequest
            .GET<Any?>("/profile")
            .cookie(token)
        val res = client.toBlocking().retrieve(httpReq)
        Assertions.assertNotNull(res)
    }

}