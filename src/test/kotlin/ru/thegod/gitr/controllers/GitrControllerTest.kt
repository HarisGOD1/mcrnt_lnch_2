package ru.thegod.gitr.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.http.HttpRequest
import io.micronaut.http.MediaType
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.thegod.gitr.GitrEntity
import ru.thegod.gitr.core.GitrRepository
import ru.thegod.gitr.core.dto.GitrEntityCreationRequestDTO
import ru.thegod.gitr.core.dto.GitrEntityResponseDTO
import ru.thegod.providers.ObjectMapperProvider
import ru.thegod.providers.TestObjectsProvider
import ru.thegod.security.user.repositories.UserRepository
import ru.thegod.security.cookies.service.CookieTokenProvider

@MicronautTest(transactional = false)
class GitrControllerTest(@Client("/gits") val client: HttpClient,
                         private val userRepository: UserRepository,
                         private val cookieTokenProvider: CookieTokenProvider,
                         private val repository: GitrRepository) {



    @BeforeEach
    fun dropTablesBeforeEach(){
        repository.deleteAll()
        userRepository.deleteAll()
    }


    @Test
    fun `save endpoint works with DB test`(){
//        TEST DATA
        val userOwner = userRepository.save(TestObjectsProvider.USER_ME)
        var testRepEntity = TestObjectsProvider.getRandomGitr(userOwner)

//        PERFORM ACTION
        val token = cookieTokenProvider.releaseCookie(userOwner)
        val request1: HttpRequest<GitrEntityCreationRequestDTO> =
            HttpRequest.POST("/save",
                GitrEntityCreationRequestDTO(
                    testRepEntity.gitrName,
                    testRepEntity.gitrDescription?:"",
                    testRepEntity.publicity)
                )
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(token)
        val body1 = client.toBlocking().retrieve(request1)


        assertNotNull(body1)

        val returnedObjectFromEndpoint = toResponseDTO_fromJsonString(body1.toString(), ObjectMapperProvider.mapper)
        val savedObjectFromDBOptional = repository.findById(returnedObjectFromEndpoint.id)
        assertNotNull(savedObjectFromDBOptional.get())
        val savedObjectFromDB = savedObjectFromDBOptional.get()

        assertEquals(testRepEntity.gitrName, (returnedObjectFromEndpoint).gitrName)
        assertEquals(testRepEntity.gitrDescription,savedObjectFromDB.gitrDescription)
        }

    @Test
    fun `get endpoint works with DB test`(){
//        TEST DATA
        val user = userRepository.save(TestObjectsProvider.USER_ME)
        var testRepEntity = TestObjectsProvider.getRandomGitr(user)
        val savedObjectFromDB = repository.save(testRepEntity)

//        PERFORM ACTION
        val request1: HttpRequest<GitrEntityResponseDTO> = HttpRequest.GET("/get/${savedObjectFromDB.id}")
        val body1 = client.toBlocking().retrieve(request1)
        assertNotNull(body1)
        val returnedObjectFromEndpoint = toResponseDTO_fromJsonString(body1.toString(), ObjectMapperProvider.mapper)


        assertEquals(savedObjectFromDB.id,returnedObjectFromEndpoint.id)
    }

    fun toResponseDTO_fromJsonString(json:String,objectMapper: ObjectMapper): GitrEntityResponseDTO {
        return objectMapper.readValue(json, GitrEntityResponseDTO::class.java)
    }

}