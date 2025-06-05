package ru.thegod.providers

import jakarta.inject.Singleton
import ru.thegod.gitr.GitrEntity
import ru.thegod.security.user.models.User
import ru.thegod.security.authentication.services.PasswordEncryptService.md5
import kotlin.random.Random

@Singleton
object TestObjectsProvider {
    val MEMBERS_LIST = listOf(
        mutableListOf("otterio","nekro25","podrivatel_jop"),
        mutableListOf("Artem","Vadim","Islam","Ildar","Ramazan","Vika","Konstantin","Lena"),
        mutableListOf("Ferkin","Gogle4456","Flipchey","HarisTheBIT"),
        mutableListOf("azAZ09","AzotX8","HarisTheBIT","HarisGG","HarisTheGOD","HarisGOD","TheGOD"),
        mutableListOf("Zelzo","dbrand","SelskoiSalaga","B5K4MC","Zax2002","panh_","KingNUBIK",
            "naum","lars20","kartOFFa","sunceg","ssshaloom"),
        mutableListOf("Azotx8","nikolaev.one","islamchik25","Genryosai Shigekuni Yamomoto"),
        mutableListOf("Bruno","iliazakharchenia","thegod")

    )
    val USER_ME = User("thegod","ppp".md5())

    fun getRandomUser():User{
        return User(null, MEMBERS_LIST.random().random(),"ppp".md5(), mutableListOf())
    }
    fun getRandomUser(password:String):User{
        return User(null, MEMBERS_LIST.random().random(),password.md5(), mutableListOf())
    }

    fun getStaticDefaultGitr(): GitrEntity {
        return GitrEntity(id = null, gitrName = "TestObjectName", USER_ME, publicity = true,
            mutableListOf(),
            gitrDescription = "smt in description", gitrCommitGenerated = null)
    }

    fun getRandomGitr(): GitrEntity {
        return GitrEntity(id = null, gitrName = "RandomTestObject"+Random.nextInt(),
            USER_ME, publicity = true,
            MEMBERS_LIST.random().shuffled().take(listOf(1,2,3).random()).toMutableList(),
            gitrDescription = "smt in description"+Random.nextInt(), gitrCommitGenerated = null)
    }

    fun getRandomGitr(owner: User): GitrEntity {
        return GitrEntity(id = null, gitrName = "RandomTestObject"+Random.nextInt(),
            owner, publicity = true,
            MEMBERS_LIST.random().shuffled().take(listOf(1,2,3).random()).toMutableList(),
            gitrDescription = "smt in description"+Random.nextInt(), gitrCommitGenerated = null)
    }
}