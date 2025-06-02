package ru.thegod.gitr.service

import io.micronaut.http.cookie.Cookie
import jakarta.inject.Singleton
import ru.thegod.gitr.GitrEntity
import ru.thegod.gitr.dto.GitrAddMembersListRequestDTO
import ru.thegod.gitr.dto.GitrEntityRequestDTO
import ru.thegod.gitr.dto.GitrEntityResponseDTO
import ru.thegod.security.User
import ru.thegod.security.UserRepository
import ru.thegod.security.cookie.CookieValidator
import java.nio.file.attribute.UserPrincipalNotFoundException
import java.security.Principal
import java.util.*
import kotlin.NoSuchElementException

@Singleton
class GitrService(private val gitrRepository: GitrRepository,
                    private val userRepository: UserRepository,
                    private val cookieValidator: CookieValidator) {

    fun getAllGitrEntities_toDTO():List<GitrEntityResponseDTO>{
        return gitrRepository.findAll().map { entity -> entity.toResponseDTO() }
    }

    fun getGitrById(id: UUID): GitrEntityResponseDTO?{
        try {
            val entity = gitrRepository.findById(id).get()
            return entity.toResponseDTO()
        }
        catch (e:NoSuchElementException){
            return null
        }
    }

    fun saveGitr(gitrEntityRequestDTO: GitrEntityRequestDTO,token: Cookie): GitrEntityResponseDTO {
        val userRequester = cookieValidator.returnUserIfAuthTokenValid(token)
        if (userRequester==null) throw Exception("Username not exist/ not found")
        return gitrRepository.save(
            GitrEntity(gitrEntityRequestDTO.gitrName,
                userRequester.username,
                userRequester,
                gitrEntityRequestDTO.publicity,
                gitrEntityRequestDTO.gitrDescription)
        ).toResponseDTO()
    }

    fun addMemberInGitr(args: GitrAddMembersListRequestDTO,token: Cookie): GitrEntityResponseDTO?{
        val user = cookieValidator.returnUserIfAuthTokenValid(token)?:return null
        var entity = gitrRepository.findById(args.repositoryId).get()
//        println(user)
//        println(entity)
        if (user.username!=entity.gitrOwnerName) return null


        for (e in args.gitrMembersNames){
            if(!entity.gitrMembersNames.contains(e)) entity.gitrMembersNames.add(e)
        }
        gitrRepository.update(entity)
        return gitrRepository.findById(args.repositoryId).get().toResponseDTO()
    }

}