package ru.thegod.gitr.service

import jakarta.inject.Singleton
import ru.thegod.gitr.GitrEntity
import ru.thegod.gitr.dto.GitrAddMembersListRequestDTO
import ru.thegod.gitr.dto.GitrEntityRequestDTO
import ru.thegod.gitr.dto.GitrEntityResponseDTO
import ru.thegod.security.UserRepository
import java.nio.file.attribute.UserPrincipalNotFoundException
import java.security.Principal
import java.util.*
import kotlin.NoSuchElementException

@Singleton
class GitrService(private val gitrRepository: GitrRepository,
                    private val userRepository: UserRepository) {

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

    fun saveGitr(gitrEntityRequestDTO: GitrEntityRequestDTO,principal: Principal): GitrEntityResponseDTO {
        val userRequester = userRepository.findByUsername(principal.name)
        if (userRequester==null) throw Exception("Username not exist/ not found")
        return gitrRepository.save(
            GitrEntity(gitrEntityRequestDTO.gitrName,
                gitrEntityRequestDTO.gitrOwnerName,
                userRequester,
                gitrEntityRequestDTO.publicity,
                gitrEntityRequestDTO.gitrDescription)
        ).toResponseDTO()
    }

    fun addMemberInGitr(args: GitrAddMembersListRequestDTO): GitrEntityResponseDTO?{
        var entity = gitrRepository.findById(args.repositoryId).get()
        for (e in args.gitrMembersNames){
            if(!entity.gitrMembersNames.contains(e)) entity.gitrMembersNames.add(e)
        }
        gitrRepository.update(entity)
        return gitrRepository.findById(args.repositoryId).get().toResponseDTO()
    }

}