package ru.thegod.gitr.core.services

import io.micronaut.http.cookie.Cookie
import jakarta.inject.Singleton
import ru.thegod.gitr.GitrEntity
import ru.thegod.gitr.core.dto.GitrEntityRequestDTO
import ru.thegod.gitr.core.dto.GitrEntityResponseDTO
import ru.thegod.gitr.core.GitrRepository
import ru.thegod.security.cookies.CookieValidator
import java.util.*
import kotlin.NoSuchElementException

@Singleton
class GitrService(private val gitrRepository: GitrRepository,
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

    fun saveGitr(gitrEntityRequestDTO: GitrEntityRequestDTO, token: Cookie): GitrEntityResponseDTO {
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


}