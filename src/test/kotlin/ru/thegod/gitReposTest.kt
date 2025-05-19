package ru.thegod

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test
import ru.thegod.userRepositories.GitRepositoryEntity
import ru.thegod.userRepositories.GitRRepository
import org.junit.jupiter.api.Assertions.assertEquals


@MicronautTest(environments = ["test"])
class gitReposTest {

    @Inject
    lateinit var repository: GitRRepository

    @Test
    fun `findByPosition returns list of Repositories`() {
        val gitRepos = listOf(
            GitRepositoryEntity(id = null, gitRepositoryName = "Striker", gitOwnerName = "thegod", publicity = true,
                membersNames = "Vadim", repositoryDescription = "abb", lastCommitGenerated = null),

            GitRepositoryEntity(id = null, gitRepositoryName = "BTR", gitOwnerName = "thegod", publicity = true,
                membersNames = "Artem", repositoryDescription = "abb", lastCommitGenerated = null)

        )

        var savedrepos = repository.saveAll(gitRepos)

        assertEquals(gitRepos[0].gitOwnerName,savedrepos[0].gitOwnerName)
        assertEquals(gitRepos[1].membersNames,savedrepos[1].membersNames)

    }

}