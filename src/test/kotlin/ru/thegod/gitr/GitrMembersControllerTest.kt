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
import ru.thegod.gitr.dto.GitrAddMembersListRequestDTO
import ru.thegod.gitr.dto.GitrEntityResponseDTO
import ru.thegod.gitr.providers.ObjectMapperProvider
import ru.thegod.gitr.providers.TestObjectsProvider
import java.util.*

@MicronautTest(transactional = false)
class GitrMembersControllerTest(@Client("/gits") val client: HttpClient) {

    @Inject
    lateinit var repository: GitrRepository

    @Test
    fun `add one member works with DB test`() {
        // TEST DATA
        var testRepEntity = TestObjectsProvider.getStaticDefaultGitr()
        val savedRepId = repository.save(testRepEntity).id!!


        // PERFORM ACTION
        val request1: HttpRequest<GitrAddMembersListRequestDTO> =
            HttpRequest.POST("/addMember", GitrAddMembersListRequestDTO(savedRepId,listOf("Pod_Jop")))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                .accept(MediaType.APPLICATION_JSON)
        // body1 contains data in JSON view, type of body1 - Kotlin.String
        val body1 = client.toBlocking().retrieve(request1)
        assertNotNull(body1)

        // ASSERTIONS
        val returnedObjectFromEndpoint = toResponseDTO_fromJsonString(body1.toString(), ObjectMapperProvider.mapper)
        val savedObjectId: UUID = returnedObjectFromEndpoint.id!! //UUID.fromString(jsonNode.get("id").asText())
        val savedObjectFromDBOptional = repository.findById(savedObjectId)
        val savedObjectFromDB = savedObjectFromDBOptional.get()


//          x6 assertions to single one
//            |
//           |
//          \/
        assertEquals(savedObjectFromDB,returnedObjectFromEndpoint.toRepositoryEntity())

    }

    @Test
    fun `add several members with some yet existing works with DB test`() {
        // TEST DATA
        var testRepEntity = TestObjectsProvider.getStaticDefaultGitr()
        val savedRepId = repository.save(testRepEntity).id!!


        // PERFORM ACTION
        val request1: HttpRequest<GitrAddMembersListRequestDTO> =
            HttpRequest.POST("/addMember", GitrAddMembersListRequestDTO(savedRepId,listOf("Pod_Jop","Otterio")))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                .accept(MediaType.APPLICATION_JSON)
        // body1 contains data in JSON view, type of body1 - Kotlin.String
        val body1 = client.toBlocking().retrieve(request1)


        // ASSERTIONS
        assertNotNull(body1)

        val returnedObjectFromEndpoint = toResponseDTO_fromJsonString(body1.toString(), ObjectMapperProvider.mapper)
        val savedObjectId: UUID = returnedObjectFromEndpoint.id!! //UUID.fromString(jsonNode.get("id").asText())

        val savedObjectFromDBOptional = repository.findById(savedObjectId)
        val savedObjectFromDB = savedObjectFromDBOptional.get()


//          x6 assertions to single one
//          |
//         |
//        \/
        assertEquals(savedObjectFromDB,returnedObjectFromEndpoint.toRepositoryEntity())
    }
    fun toResponseDTO_fromJsonString(json:String,objectMapper: ObjectMapper): GitrEntityResponseDTO {
        return objectMapper.readValue(json, GitrEntityResponseDTO::class.java)
    }
}