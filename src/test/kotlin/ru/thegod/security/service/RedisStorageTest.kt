package ru.thegod.security.service

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.thegod.security.cookie.ExpiredTokenStorage

@MicronautTest
class RedisStorageTest {

    @Inject
    lateinit var storage: ExpiredTokenStorage

    @BeforeEach
    fun setUp() {
        storage.selectDatabase(15)
        storage.flushDB()
    }

    @Test
    fun `test connection`(){
        storage.putItem("alice","bob")
        assertEquals("bob",storage.getItem("alice").get())
    }

    @Test
    fun `test size`(){
        assertEquals(0,storage.getSize())
        storage.putItem("alice","bob")
        assertEquals(1,storage.getSize())

    }


    @Test
    fun `print debug info to tests output`(){
        println(storage.info())

    }
}