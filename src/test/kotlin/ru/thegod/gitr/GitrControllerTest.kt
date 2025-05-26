package ru.thegod.gitr

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
import ru.thegod.gitr.service.GitrRepository
import ru.thegod.gitr.dto.GitrEntityRequestDTO
import ru.thegod.gitr.dto.GitrEntityResponseDTO
import ru.thegod.gitr.providers.ObjectMapperProvider
import ru.thegod.gitr.providers.TestObjectsProvider

@MicronautTest(transactional = false)
class GitrControllerTest(@Client("/gits") val client: HttpClient) {

    @Inject
    lateinit var repository: GitrRepository

    @Test
    fun `save endpoint works with DB test`(){
//        TEST DATA
        var testRepEntity = TestObjectsProvider.getStaticDefaultGitr()

//        PERFORM ACTION
        val request1: HttpRequest<GitrEntityRequestDTO> =
            HttpRequest.POST("/save",
                GitrEntityRequestDTO(testRepEntity)
                )
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                .accept(MediaType.APPLICATION_JSON)
        // body1 contains data in JSON view, type of body1 - Kotlin.String
        val body1 = client.toBlocking().retrieve(request1)
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
        assertEquals(testRepEntity,returnedObjectFromEndpoint.toRepositoryEntity())

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
        assertEquals(savedObjectFromDB,returnedObjectFromEndpoint.toRepositoryEntity())
    }

    fun toResponseDTO_fromJsonString(json:String,objectMapper: ObjectMapper): GitrEntityResponseDTO {
        return objectMapper.readValue(json, GitrEntityResponseDTO::class.java)
    }

}