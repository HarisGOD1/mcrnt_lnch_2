package ru.thegod.gitr.service

import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject

@MicronautTest(transactional = false)
class GitrServiceTest(@Client("/gits") val client: HttpClient) {

    @Inject
    lateinit var repository: GitrRepository


}