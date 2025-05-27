package ru.thegod.gitr.controllers

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import ru.thegod.providers.TestObjectsProvider
import ru.thegod.gitr.service.GitrRepository

@MicronautTest(transactional = false)
class GitrPageTest(@Client("/gits") val client: HttpClient) {

    @Inject
    lateinit var repository: GitrRepository

    @Test
    fun `save gitr form page exists test`() {
        val request: HttpRequest<Any> = HttpRequest.GET("/form/saveGitr")
        val body = client.toBlocking().retrieve(request)
        assertNotNull(body)
    }

    @Test
    fun `add members form page exists test`() {
        val e = repository.save(TestObjectsProvider.getRandomGitr())
        val request: HttpRequest<Any> = HttpRequest.GET("/form/addMember/${e.id}")
        val body = client.toBlocking().retrieve(request)
        assertNotNull(body)
    }


}