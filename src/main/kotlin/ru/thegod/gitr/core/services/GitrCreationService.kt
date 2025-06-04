package ru.thegod.gitr.core.services

import io.micronaut.http.cookie.Cookie
import jakarta.inject.Singleton
import ru.thegod.gitr.GitrEntity
import ru.thegod.gitr.core.dto.GitrEntityResponseDTO
import ru.thegod.gitr.core.GitrRepository
import ru.thegod.gitr.core.dto.GitrEntityCreationRequestDTO
import ru.thegod.security.cookies.service.CookieValidator

@Singleton
class GitrCreationService(private val gitrRepository: GitrRepository,
                          private val cookieValidator: CookieValidator) {

    fun saveGitr(gitrCreationRequestDTO: GitrEntityCreationRequestDTO, token: Cookie): GitrEntityResponseDTO {
        val userRequester = cookieValidator.returnUserIfAuthTokenValid(token)
        if (userRequester==null) throw Exception("Username not exist/ not found")
        return GitrEntityResponseDTO(gitrRepository.save(
            GitrEntity(gitrCreationRequestDTO.gitrName,
                userRequester.id!!,
                gitrCreationRequestDTO.publicity,
                gitrCreationRequestDTO.gitrDescription)
                )
            )
    }


}