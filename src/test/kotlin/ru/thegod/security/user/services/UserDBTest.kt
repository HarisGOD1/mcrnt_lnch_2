package ru.thegod.security.user.services

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.thegod.providers.TestObjectsProvider
import ru.thegod.security.user.repositories.UserRepository

@MicronautTest
class UserDBTest {
    @Inject
    lateinit var userRepository: UserRepository


    @Test
    fun `save list dumb test`() {
        val listuser = mutableListOf(
            TestObjectsProvider.getRandomUser(),
                            TestObjectsProvider.getRandomUser(),
                            TestObjectsProvider.getRandomUser(),
        )

        val savedListFromDB = userRepository.saveAll(listuser)

        println(savedListFromDB)
        println(listuser)
        Assertions.assertEquals(listuser, savedListFromDB)

    }
}