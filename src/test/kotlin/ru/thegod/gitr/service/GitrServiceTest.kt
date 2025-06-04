package ru.thegod.gitr.service

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import ru.thegod.gitr.core.GitrRepository

@MicronautTest(transactional = false)
class GitrServiceTest() {

    @Inject
    lateinit var repository: GitrRepository


}