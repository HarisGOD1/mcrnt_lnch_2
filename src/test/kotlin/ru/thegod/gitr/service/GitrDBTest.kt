package ru.thegod.gitr.service

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import ru.thegod.gitr.core.GitrRepository
import ru.thegod.providers.TestObjectsProvider
import ru.thegod.security.user.repositories.UserRepository


@MicronautTest(environments = ["test"],startApplication = false, transactional = false)
class GitrDBTest {

    @Inject
    lateinit var gitrRepository: GitrRepository
    @Inject
    lateinit var userRepository: UserRepository


    @Test
    fun `save list dumb test`() {
        val user1 = userRepository.save(TestObjectsProvider.getRandomUser())
        val user2 = userRepository.save(TestObjectsProvider.getRandomUser())
        val gitRepos = listOf(
            TestObjectsProvider.getRandomGitr(user1),//TO-DO: test may fall randomly
            TestObjectsProvider.getRandomGitr(user2)

        )

        var savedrepos = gitrRepository.saveAll(gitRepos)

        assertEquals(gitRepos[0].gitrOwner!!.id,savedrepos[0].gitrOwner!!.id)
        assertEquals(gitRepos[1].gitrMembersNames,savedrepos[1].gitrMembersNames)

    }

    @Test
    fun `save repositories in database full test`() {
        val gitRepos = listOf(
            TestObjectsProvider.getRandomGitr(TestObjectsProvider.getRandomUser()),
            TestObjectsProvider.getRandomGitr(TestObjectsProvider.getRandomUser()),
            TestObjectsProvider.getRandomGitr(TestObjectsProvider.getRandomUser())
            )

        var savedrepos = gitrRepository.saveAll(gitRepos)

        var repNum: Int = 0

        while(repNum<gitRepos.size){
            assertEquals(gitRepos[repNum].gitrOwner,savedrepos[repNum].gitrOwner)
            assertEquals(gitRepos[repNum].gitrName,savedrepos[repNum].gitrName)
            assertEquals(gitRepos[repNum].publicity,savedrepos[repNum].publicity)
            assertEquals(gitRepos[repNum].gitrMembersNames,savedrepos[repNum].gitrMembersNames)
            assertEquals(gitRepos[repNum].gitrDescription,savedrepos[repNum].gitrDescription)
            assertEquals(gitRepos[repNum].gitrCommitGenerated,savedrepos[repNum].gitrCommitGenerated)
            repNum++
        }

    }

    @Test
    fun `save in repositories with actual user and user must contain all owned repos`(){

        var testUser = TestObjectsProvider.getRandomUser()
        testUser = userRepository.save(testUser)

        var testGitr = TestObjectsProvider.getRandomGitr(testUser)
        testGitr = gitrRepository.save(testGitr)




        val testUserFromDB = userRepository.getById(testUser.id!!).get()
        val testGitrFromDB = gitrRepository.getById(testGitr.id!!)
        assertEquals(testGitrFromDB.get().gitrOwner!!.id,testUserFromDB.id)



    }

}