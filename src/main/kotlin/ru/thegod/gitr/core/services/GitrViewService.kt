package ru.thegod.gitr.core.services

import jakarta.inject.Singleton
import ru.thegod.gitr.core.GitrRepository
import ru.thegod.gitr.core.dto.GitrEntityResponseDTO
import ru.thegod.security.cookies.service.CookieValidator
import java.util.UUID

@Singleton
class GitrViewService(private val gitrRepository: GitrRepository,
                          private val cookieValidator: CookieValidator) {

    fun getAllGitrEntities_toDTO():List<GitrEntityResponseDTO>{
        return gitrRepository.findAll().map { entity -> GitrEntityResponseDTO(entity) }
    }

    fun getGitrById(id: UUID): GitrEntityResponseDTO?{
        try {
            val entity = gitrRepository.findById(id).get()
            return GitrEntityResponseDTO(entity)
        }
        catch (e:NoSuchElementException){
            return null
        }
    }
}