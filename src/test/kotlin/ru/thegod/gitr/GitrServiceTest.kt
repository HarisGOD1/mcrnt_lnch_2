package ru.thegod.gitr

import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import ru.thegod.gitr.service.GitrRepository

@MicronautTest(transactional = false)
class GitrServiceTest(@Client("/gits") val client: HttpClient) {

    @Inject
    lateinit var repository: GitrRepository


}