package ru.thegod

import io.micronaut.http.HttpRequest
import io.micronaut.http.MediaType
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import ru.thegod.userRepositories.GitRRepository
import ru.thegod.userRepositories.GitRepositoryController
import ru.thegod.userRepositories.GitRepositoryEntity

@MicronautTest
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
           //TO-DO: replace mutable list, maybe with null
            var testRep = GitRepositoryEntity(id = null, gitRepositoryName = "ACLManager", gitOwnerName = "thegod", publicity = true,
               mutableListOf(), repositoryDescription = "linux acl manager based on python", lastCommitGenerated = null)

                val request1: HttpRequest<GitRepositoryController.Request> = HttpRequest.POST("/save",
                GitRepositoryController.Request(testRep.gitRepositoryName, testRep.gitOwnerName,
                        testRep.publicity,testRep.repositoryDescription)
            ).contentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE).accept(MediaType.APPLICATION_JSON)
//                "gitRepositoryName=${testRep.gitRepositoryName}&" +
//                    "repositoryDescription=${testRep.repositoryDescription}&" +
//                    "publicity=${testRep.publicity}&"+
//                    "gitOwnerName=${testRep.gitOwnerName}")
            println("HA")
            val body1 = client.toBlocking().retrieve(request1)
            assertNotNull(body1)

        println("HA")
            val savedObjectId = 1L // TO-DO read from page actual ID

        println("HA")
            val savedObjectFromDBOptional = repository.findById(savedObjectId)
            assertNotNull(savedObjectFromDBOptional.get())
            val savedObjectFromDB = savedObjectFromDBOptional.get()

            assertEquals(testRep.gitOwnerName,savedObjectFromDB.gitOwnerName)
            assertEquals(testRep.gitRepositoryName,savedObjectFromDB.gitRepositoryName)
            assertEquals(testRep.publicity,savedObjectFromDB.publicity)
            assertEquals(testRep.membersNames,savedObjectFromDB.membersNames)
            assertEquals(testRep.repositoryDescription,savedObjectFromDB.repositoryDescription)
            assertEquals(testRep.lastCommitGenerated,savedObjectFromDB.lastCommitGenerated)
        }

}