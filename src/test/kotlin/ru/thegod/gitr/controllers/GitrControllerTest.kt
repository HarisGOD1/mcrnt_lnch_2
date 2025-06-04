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
import org.junit.jupiter.api.Test
import ru.thegod.gitr.GitrEntity
import ru.thegod.gitr.core.GitrRepository
import ru.thegod.gitr.core.dto.GitrEntityCreationRequestDTO
import ru.thegod.gitr.core.dto.GitrEntityRequestDTO
import ru.thegod.gitr.core.dto.GitrEntityResponseDTO
import ru.thegod.providers.ObjectMapperProvider
import ru.thegod.providers.TestObjectsProvider
import ru.thegod.security.user.repositories.UserRepository
import ru.thegod.security.cookies.service.CookieTokenProvider

@MicronautTest(transactional = false)
class GitrControllerTest(@Client("/gits") val client: HttpClient,
                         private val userRepository: UserRepository,
                         private val cookieTokenProvider: CookieTokenProvider) {

    @Inject
    lateinit var repository: GitrRepository

    @Test
    fun `save endpoint works with DB test`(){
//        TEST DATA
        val userOwner = TestObjectsProvider.USER_ME
        var testRepEntity = TestObjectsProvider.getStaticDefaultGitr()

//        PERFORM ACTION
        userRepository.save(userOwner)
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
        // body1 contains data in JSON view, type of body1 - Kotlin.String
//        println(request1.body)

        val body1 = client.toBlocking().retrieve(request1)

//        assertNotNull(httpResponse)
//        val body1 = httpResponse.body
//        println(body1)
//        println(body1.toString())
        assertNotNull(body1)

        val returnedObjectFromEndpoint = toResponseDTO_fromJsonString(body1.toString(), ObjectMapperProvider.mapper)
        val savedObjectFromDBOptional = repository.findById(returnedObjectFromEndpoint.id)
        assertNotNull(savedObjectFromDBOptional.get())
        val savedObjectFromDB = savedObjectFromDBOptional.get()

//        ASSERT DATA
//            x6 assertions to one
//            |
//           |
//          \/
        assertEquals(testRepEntity,savedObjectFromDB)
        assertEquals(testRepEntity.gitrName, (returnedObjectFromEndpoint).gitrName)


        }

    @Test
    fun `get endpoint works with DB test`(){
//        TEST DATA
        var testRepEntity = TestObjectsProvider.getStaticDefaultGitr()
        val savedObjectFromDB = repository.save(testRepEntity)

//        PERFORM ACTION
        val request1: HttpRequest<GitrEntityResponseDTO> = HttpRequest.GET("/get/${savedObjectFromDB.id}")
        val body1 = client.toBlocking().retrieve(request1)
        assertNotNull(body1)
        val returnedObjectFromEndpoint = toResponseDTO_fromJsonString(body1.toString(), ObjectMapperProvider.mapper)


//        ASSERT DATA
//            x6 assertions to one
//            |
//           |
//          \/
//        println(savedObjectFromDB.toString())
//        println(returnedObjectFromEndpoint.toString())
//        println(returnedObjectFromEndpoint.toRepositoryEntity().toString())
        assertEquals(savedObjectFromDB.gitrOwnerId,returnedObjectFromEndpoint.gitrOwnerId)
    }

    fun toResponseDTO_fromJsonString(json:String,objectMapper: ObjectMapper): GitrEntityResponseDTO {
        return objectMapper.readValue(json, GitrEntityResponseDTO::class.java)
    }

}