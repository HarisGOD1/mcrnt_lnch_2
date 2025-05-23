package ru.thegod

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
import ru.thegod.userRepositories.*

@MicronautTest(transactional = false)
class GitR_Controller_Test(@Client("/gits") val client: HttpClient) {

    @Inject
    lateinit var repository: GitRRepository

    @Test
    fun `form page exist test`() {
        val request: HttpRequest<Any> = HttpRequest.GET("/form")
        val body = client.toBlocking().retrieve(request)
        assertNotNull(body)
    }

    @Test
    fun `save endpoint works with DB test`(){
//        TEST DATA
        var testRepEntity = GitRepositoryEntity(id = null, gitRepositoryName = "ACLManager", gitOwnerName = "thegod", publicity = true,
           mutableListOf(), repositoryDescription = "linux acl manager based on python", lastCommitGenerated = null)


//        PERFORM ACTION
        val request1: HttpRequest<GitRepositoryEntityRequestDTO> =
            HttpRequest.POST("/save",
                GitRepositoryEntityRequestDTO(testRepEntity.gitRepositoryName, testRepEntity.gitOwnerName,
                    testRepEntity.publicity,testRepEntity.repositoryDescription)
                ).contentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE).accept(MediaType.APPLICATION_JSON)
        // body1 contains data in JSON view, type of body1 - Kotlin.String
        val body1 = client.toBlocking().retrieve(request1)
        assertNotNull(body1)

        val mapper = ObjectMapper()
        val jsonNode = mapper.readTree(body1)
        val savedObjectId:Long = jsonNode.get("id").asText().toLong()

        val savedObjectFromDBOptional = repository.findById(savedObjectId)
        assertNotNull(savedObjectFromDBOptional.get())
        val savedObjectFromDB = savedObjectFromDBOptional.get()

//        ASSERT DATA
//        assertEquals(testRepEntity.gitOwnerName,          savedObjectFromDB.gitOwnerName)
//        assertEquals(testRepEntity.gitRepositoryName,     savedObjectFromDB.gitRepositoryName)
//        assertEquals(testRepEntity.publicity,             savedObjectFromDB.publicity)
//        assertEquals(testRepEntity.membersNames,          savedObjectFromDB.membersNames)
//        assertEquals(testRepEntity.repositoryDescription, savedObjectFromDB.repositoryDescription)
//        assertEquals(testRepEntity.lastCommitGenerated,   savedObjectFromDB.lastCommitGenerated)

        assertEquals(testRepEntity,savedObjectFromDB)

        }

    @Test
    fun `get endpoint works with DB test`(){
//        TEST DATA
        var testRepEntity = GitRepositoryEntity(id = null, gitRepositoryName = "Djit_web_project",
            gitOwnerName = "thegod", publicity = true,
            mutableListOf("Otterio","Nekro25"), repositoryDescription = "github-like web app",
            lastCommitGenerated = "added ai")


//        PERFORM ACTION
        val savedObjectFromDB = repository.save(testRepEntity)
        val request1: HttpRequest<GitRepositoryEntityResponseDTO> = HttpRequest.GET("/get/${savedObjectFromDB.id}")
        val body1 = client.toBlocking().retrieve(request1)
        assertNotNull(body1)
        val mapper = ObjectMapper()
        val returnedObjectFromEndpoint = toResponseDTO_fromJsonString(body1.toString(), mapper)


//        ASSERT DATA
//        assertEquals(savedObjectFromDB.id,jsonNode.get("id").asLong())
//        assertEquals(savedObjectFromDB.gitOwnerName,jsonNode.get("gitOwnerName").asText())
//        assertEquals(savedObjectFromDB.gitRepositoryName,jsonNode.get("gitRepositoryName").asText())
//        assertEquals(savedObjectFromDB.publicity,jsonNode.get("publicity").asBoolean())
//        for (index in savedObjectFromDB.membersNames.indices){
//            assertEquals(savedObjectFromDB.membersNames[index],jsonNode.get("membersNames")[index].asText())
//        }
//        assertEquals(savedObjectFromDB.repositoryDescription,jsonNode.get("repositoryDescription").asText())
//        assertEquals(savedObjectFromDB.lastCommitGenerated,jsonNode.get("lastCommitGenerated").asText())
//            |
//           |
//          \/
        assertEquals(savedObjectFromDB,returnedObjectFromEndpoint.toRepositoryEntity())
    }

    @Test
    fun `add one member works with DB test`() {
        // TEST DATA
        var testRepEntity = GitRepositoryEntity(id = null, gitRepositoryName = "Djit_web_project",
            gitOwnerName = "thegod", publicity = true,
            mutableListOf("Otterio","Nekro25"), repositoryDescription = "github-like web app",
            lastCommitGenerated = "added ai")
        val savedRepId = repository.save(testRepEntity).id!!


        // PERFORM ACTION
        val request1: HttpRequest<GitRepositoryAddMemberListRequestDTO> =
            HttpRequest.POST("/addMember",GitRepositoryAddMemberListRequestDTO(savedRepId,listOf("Pod_Jop"))
            ).contentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE).accept(MediaType.APPLICATION_JSON)
        // body1 contains data in JSON view, type of body1 - Kotlin.String
        val body1 = client.toBlocking().retrieve(request1)
        assertNotNull(body1)

//        println(request1.body.toString())
//        println(request1.body)
//        println(body1)

        // ASSERTIONS
        val mapper = ObjectMapper()
        val jsonNode = mapper.readTree(body1)
        val savedObjectId:Long = jsonNode.get("id").asText().toLong()
        val savedObjectFromDBOptional = repository.findById(savedObjectId)
        val savedObjectFromDB = savedObjectFromDBOptional.get()
        val returnedObjectFromEndpoint = toResponseDTO_fromJsonString(body1.toString(), mapper)


//        assertEquals(savedObjectFromDB.id,                  returnedObjectFromEndpoint.id)
//        assertEquals(savedObjectFromDB.gitOwnerName,        returnedObjectFromEndpoint.gitOwnerName)
//        assertEquals(savedObjectFromDB.gitRepositoryName,   returnedObjectFromEndpoint.gitRepositoryName)
//        assertEquals(savedObjectFromDB.publicity,           returnedObjectFromEndpoint.publicity)
//        for (index in savedObjectFromDB.membersNames.indices){
//            assertEquals(savedObjectFromDB.membersNames[index],returnedObjectFromEndpoint.membersNames[index])
//        }
//        assertEquals(savedObjectFromDB.repositoryDescription,returnedObjectFromEndpoint.repositoryDescription)
//        assertEquals(savedObjectFromDB.lastCommitGenerated, returnedObjectFromEndpoint.lastCommitGenerated)
//            |
//           |
//          \/
        println(savedObjectFromDB)
        println(returnedObjectFromEndpoint)
        assertEquals(savedObjectFromDB,returnedObjectFromEndpoint.toRepositoryEntity())

    }

    @Test
    fun `add several members with some yet existing works with DB test`() {
        // TEST DATA
        var testRepEntity = GitRepositoryEntity(id = null, gitRepositoryName = "Djit_web_project",
            gitOwnerName = "thegod", publicity = true,
            mutableListOf("Otterio","Nekro25"), repositoryDescription = "github-like web app",
            lastCommitGenerated = "added ai")
        val savedRepId = repository.save(testRepEntity).id!!


        // PERFORM ACTION
        val request1: HttpRequest<GitRepositoryAddMemberListRequestDTO> =
            HttpRequest.POST("/addMember",GitRepositoryAddMemberListRequestDTO(savedRepId,listOf("Pod_Jop","Otterio"))
            ).contentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE).accept(MediaType.APPLICATION_JSON)
        // body1 contains data in JSON view, type of body1 - Kotlin.String
        val body1 = client.toBlocking().retrieve(request1)




        // ASSERTIONS
        assertNotNull(body1)

        val mapper = ObjectMapper()
        val jsonNode = mapper.readTree(body1)
        val savedObjectId:Long = jsonNode.get("id").asText().toLong()
        val savedObjectFromDBOptional = repository.findById(savedObjectId)
        val savedObjectFromDB = savedObjectFromDBOptional.get()
        val returnedObjectFromEndpoint = toResponseDTO_fromJsonString(body1.toString(), mapper)


//        assertEquals(savedObjectFromDB.id,                  returnedObjectFromEndpoint.id)
//        assertEquals(savedObjectFromDB.gitOwnerName,        returnedObjectFromEndpoint.gitOwnerName)
//        assertEquals(savedObjectFromDB.gitRepositoryName,   returnedObjectFromEndpoint.gitRepositoryName)
//        assertEquals(savedObjectFromDB.publicity,           returnedObjectFromEndpoint.publicity)
//        for (index in savedObjectFromDB.membersNames.indices){
//            assertEquals(savedObjectFromDB.membersNames[index],returnedObjectFromEndpoint.membersNames[index])
//        }
//        assertEquals(savedObjectFromDB.repositoryDescription,returnedObjectFromEndpoint.repositoryDescription)
//        assertEquals(savedObjectFromDB.lastCommitGenerated, returnedObjectFromEndpoint.lastCommitGenerated)
//          |
//         |
//        \/
        assertEquals(savedObjectFromDB,returnedObjectFromEndpoint.toRepositoryEntity())
    }

    fun toResponseDTO_fromJsonString(json:String,objectMapper: ObjectMapper):GitRepositoryEntityResponseDTO{
        return objectMapper.readValue(json,GitRepositoryEntityResponseDTO::class.java)
    }
}