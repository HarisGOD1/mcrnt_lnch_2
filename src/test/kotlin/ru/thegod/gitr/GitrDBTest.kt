package ru.thegod.gitr

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test
import ru.thegod.gitr.service.GitrRepository
import org.junit.jupiter.api.Assertions.assertEquals


@MicronautTest(environments = ["test"])
class GitrDBTest {

    @Inject
    lateinit var repository: GitrRepository


    @Test
    fun `save list dumb test`() {
        val gitRepos = listOf(
            GitrEntity(id = null, gitrName = "Striker", gitrOwnerName = "thegod", publicity = true,
                gitrMembersNames = mutableListOf("Vadim","Artem"), gitrDescription = "abb", gitrCommitGenerated = null),

            GitrEntity(id = null, gitrName = "BTR", gitrOwnerName = "thegod", publicity = true,
                mutableListOf("Vadim","Artem"), gitrDescription = "abb", gitrCommitGenerated = null)

        )

        var savedrepos = repository.saveAll(gitRepos)

        assertEquals(gitRepos[0].gitrOwnerName,savedrepos[0].gitrOwnerName)
        assertEquals(gitRepos[1].gitrMembersNames,savedrepos[1].gitrMembersNames)

    }

    @Test
    fun `save repositories in database full test`() {
        val gitRepos = listOf(
            GitrEntity(id = null, gitrName = "urban octo spoon", gitrOwnerName = "thegod", publicity = true,
                gitrMembersNames = mutableListOf("Vadim","Artem"), gitrDescription = "8/ 10 spoons", gitrCommitGenerated = null),

            GitrEntity(id = null, gitrName = "education forum", gitrOwnerName = "thegod", publicity = true,
                mutableListOf("Vadim","Artem","VIKA","Konstantin"), gitrDescription = "forummm", gitrCommitGenerated = null),

            GitrEntity(id = null, gitrName = "ACLManager", gitrOwnerName = "thegod", publicity = true,
                mutableListOf(), gitrDescription = "linux acl manager based on python", gitrCommitGenerated = null)
        )

        var savedrepos = repository.saveAll(gitRepos)

        var repNum: Int = 0

        while(repNum<gitRepos.size){
            assertEquals(gitRepos[repNum].gitrOwnerName,savedrepos[repNum].gitrOwnerName)
            assertEquals(gitRepos[repNum].gitrName,savedrepos[repNum].gitrName)
            assertEquals(gitRepos[repNum].publicity,savedrepos[repNum].publicity)
            assertEquals(gitRepos[repNum].gitrMembersNames,savedrepos[repNum].gitrMembersNames)
            assertEquals(gitRepos[repNum].gitrDescription,savedrepos[repNum].gitrDescription)
            assertEquals(gitRepos[repNum].gitrCommitGenerated,savedrepos[repNum].gitrCommitGenerated)
            repNum++
        }

    }



}