package ru.thegod.gitr.controllers

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import ru.thegod.providers.TestObjectsProvider
import ru.thegod.gitr.core.GitrRepository
import ru.thegod.security.user.UserRepository
import ru.thegod.security.cookies.service.CookieTokenProvider

@MicronautTest(transactional = false)
class GitrPageTest(@Client("/gits") val client: HttpClient) {

    @Inject
    lateinit var repository: GitrRepository
    @Inject
    lateinit var userRepository: UserRepository
    @Inject
    lateinit var tokenProvider: CookieTokenProvider

    @Test
    fun `save gitr form page exists test`() {
        val request: HttpRequest<Any> = HttpRequest.GET("/form/saveGitr")
        val body = client.toBlocking().retrieve(request)
        assertNotNull(body)
    }

    @Test
    fun `add members form page exists test`() {
        val user = userRepository.save(TestObjectsProvider.USER_ME)

        val e = repository.save(TestObjectsProvider.getStaticDefaultGitr())
        val cookie = tokenProvider.releaseCookie(user)

        val request: HttpRequest<Any> = HttpRequest.GET<Any?>("/form/addMember/${e.id}").cookie(cookie)
        val body = client.toBlocking().retrieve(request)
        assertNotNull(body)
    }


}