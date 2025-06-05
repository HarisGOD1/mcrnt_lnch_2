package ru.thegod.security.user

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.thegod.providers.TestObjectsProvider
import ru.thegod.security.user.repositories.UserRepository
import ru.thegod.security.user.roles.UserRole

@MicronautTest(transactional = false)
class UserRolesTest() {
    @Inject
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun dropTablesBeforeEach(){
        userRepository.deleteAll()
    }


    @Test
    fun testDatabaseIsOK(){
        val user = userRepository.save(TestObjectsProvider.USER_ME)
        println("1"+user)
        user.roles.add(UserRole.THIRD_USER)
        userRepository.update(user)
        val userFromDB = userRepository.getById(user.id!!)
        assertNotNull(userFromDB)
        assertEquals(user,userFromDB.get())
    }
}