package ru.thegod.gitr.service

import jakarta.inject.Singleton
import ru.thegod.gitr.GitrEntity
import ru.thegod.gitr.dto.GitrAddMembersListRequestDTO
import ru.thegod.gitr.dto.GitrEntityRequestDTO
import ru.thegod.gitr.dto.GitrEntityResponseDTO
import java.util.*
import kotlin.NoSuchElementException

@Singleton
class GitrService(private val repository: GitrRepository) {

    fun getAllGitrEntities_toDTO():List<GitrEntityResponseDTO>{
        return repository.findAll().map { entity -> entity.toResponseDTO() }
    }

    fun getGitrById(id: UUID): GitrEntityResponseDTO?{
        try {
            val entity = repository.findById(id).get()
            return entity.toResponseDTO()
        }
        catch (e:NoSuchElementException){
            return null
        }
    }

    fun saveGitr(gitrEntityRequestDTO: GitrEntityRequestDTO): GitrEntityResponseDTO {
        return repository.save(
            GitrEntity(gitrEntityRequestDTO.gitrName,
                gitrEntityRequestDTO.gitrOwnerName,
                gitrEntityRequestDTO.publicity,
                gitrEntityRequestDTO.gitrDescription)
        ).toResponseDTO()
    }

    fun addMemberInGitr(args: GitrAddMembersListRequestDTO): GitrEntityResponseDTO?{
        var entity = repository.findById(args.repositoryId).get()
        for (e in args.gitrMembersNames){
            if(!entity.gitrMembersNames.contains(e)) entity.gitrMembersNames.add(e)
        }
        repository.update(entity)
        return repository.findById(args.repositoryId).get().toResponseDTO()
    }

}