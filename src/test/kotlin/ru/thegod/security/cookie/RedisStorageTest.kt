package ru.thegod.security.cookie

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.thegod.security.cookies.storage.ExpiredTokenStorage

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
        Assertions.assertEquals("bob", storage.getItem("alice").get())
    }

    @Test
    fun `test size`(){
        Assertions.assertEquals(0, storage.getSize())
        storage.putItem("alice","bob")
        Assertions.assertEquals(1, storage.getSize())

    }


    @Test
    fun `print debug info to tests output`(){
        println(storage.info())

    }
}