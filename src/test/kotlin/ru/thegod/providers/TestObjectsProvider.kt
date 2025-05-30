package ru.thegod.providers

import jakarta.inject.Singleton
import ru.thegod.gitr.GitrEntity
import ru.thegod.security.User
import kotlin.random.Random

@Singleton
object TestObjectsProvider {
    val membersListList = listOf(
        mutableListOf("otterio","nekro25","podrivatel_jop"),
        mutableListOf("Artem","Vadim","Islam","Ildar","Ramazan","Vika","Konstantin","Lena"),
        mutableListOf("Ferkin","Gogle4456","Flipchey","HarisTheBIT"),
        mutableListOf("azAZ09","AzotX8","HarisTheBIT","HarisGG","HarisTheGOD","HarisGOD","TheGOD"),
        mutableListOf("Zelzo","dbrand","SelskoiSalaga","B5K4MC","Zax2002","panh_","KingNUBIK",
            "naum","lars20","kartOFFa","sunceg","ssshaloom"),
        mutableListOf("Azotx8","nikolaev.one","islamchik25","Genryosai Shigekuni Yamomoto"),
        mutableListOf("Bruno","iliazakharchenia","thegod")

    )
    val userMe = User(null,"thegod","ppp", mutableListOf())

    fun getRandomUser():User{
        return User(null, membersListList.random().random(),"ppp", mutableListOf())
    }

    fun getStaticDefaultGitr(): GitrEntity {
        return GitrEntity(id = null, gitrName = "TestObjectName",
            gitrOwnerName = "thegod", userMe, publicity = true,
            mutableListOf(),
            gitrDescription = "smt in description", gitrCommitGenerated = null)
    }

    fun getRandomGitr(): GitrEntity {
        return GitrEntity(id = null, gitrName = "RandomTestObject"+Random.nextInt(),
            gitrOwnerName = "thegod", userMe, publicity = true,
            membersListList.random().shuffled().take(listOf(1,2,3).random()).toMutableList(),
            gitrDescription = "smt in description"+Random.nextInt(), gitrCommitGenerated = null)
    }

    fun getRandomGitr(owner: User): GitrEntity {
        return GitrEntity(id = null, gitrName = "RandomTestObject"+Random.nextInt(),
            gitrOwnerName = owner.username, owner, publicity = true,
            membersListList.random().shuffled().take(listOf(1,2,3).random()).toMutableList(),
            gitrDescription = "smt in description"+Random.nextInt(), gitrCommitGenerated = null)
    }
}