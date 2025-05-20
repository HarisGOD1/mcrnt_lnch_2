package ru.thegod

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test
import ru.thegod.userRepositories.GitRepositoryEntity
import ru.thegod.userRepositories.GitRRepository
import org.junit.jupiter.api.Assertions.assertEquals


@MicronautTest(environments = ["test"])
class GitR_DB_Test {

    @Inject
    lateinit var repository: GitRRepository


    @Test
    fun `save list dumb test`() {
        val gitRepos = listOf(
            GitRepositoryEntity(id = null, gitRepositoryName = "Striker", gitOwnerName = "thegod", publicity = true,
                membersNames = mutableListOf("Vadim","Artem"), repositoryDescription = "abb", lastCommitGenerated = null),

            GitRepositoryEntity(id = null, gitRepositoryName = "BTR", gitOwnerName = "thegod", publicity = true,
                mutableListOf("Vadim","Artem"), repositoryDescription = "abb", lastCommitGenerated = null)

        )

        var savedrepos = repository.saveAll(gitRepos)

        assertEquals(gitRepos[0].gitOwnerName,savedrepos[0].gitOwnerName)
        assertEquals(gitRepos[1].membersNames,savedrepos[1].membersNames)

    }

    @Test
    fun `save repositories in database full test`() {
        val gitRepos = listOf(
            GitRepositoryEntity(id = null, gitRepositoryName = "urban octo spoon", gitOwnerName = "thegod", publicity = true,
                membersNames = mutableListOf("Vadim","Artem"), repositoryDescription = "8/ 10 spoons", lastCommitGenerated = null),

            GitRepositoryEntity(id = null, gitRepositoryName = "education forum", gitOwnerName = "thegod", publicity = true,
                mutableListOf("Vadim","Artem","VIKA","Konstantin"), repositoryDescription = "forummm", lastCommitGenerated = null),

            GitRepositoryEntity(id = null, gitRepositoryName = "ACLManager", gitOwnerName = "thegod", publicity = true,
                mutableListOf(), repositoryDescription = "linux acl manager based on python", lastCommitGenerated = null)
        )

        var savedrepos = repository.saveAll(gitRepos)

        var repNum: Int = 0

        while(repNum<gitRepos.size){
            assertEquals(gitRepos[repNum].gitOwnerName,savedrepos[repNum].gitOwnerName)
            assertEquals(gitRepos[repNum].gitRepositoryName,savedrepos[repNum].gitRepositoryName)
            assertEquals(gitRepos[repNum].publicity,savedrepos[repNum].publicity)
            assertEquals(gitRepos[repNum].membersNames,savedrepos[repNum].membersNames)
            assertEquals(gitRepos[repNum].repositoryDescription,savedrepos[repNum].repositoryDescription)
            assertEquals(gitRepos[repNum].lastCommitGenerated,savedrepos[repNum].lastCommitGenerated)
            repNum++
        }

    }



}