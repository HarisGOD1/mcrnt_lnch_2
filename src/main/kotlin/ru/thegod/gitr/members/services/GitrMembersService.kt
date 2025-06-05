package ru.thegod.gitr.members.services

import io.micronaut.http.HttpResponse
import io.micronaut.http.cookie.Cookie
import jakarta.inject.Singleton
import ru.thegod.gitr.members.dto.GitrAddMembersListRequestDTO
import ru.thegod.gitr.core.dto.GitrEntityResponseDTO
import ru.thegod.gitr.core.GitrRepository
import ru.thegod.security.cookies.service.CookieValidator
import java.util.*

@Singleton
class GitrMembersService(private val gitrRepository: GitrRepository,
                         private val cookieValidator: CookieValidator
) {
    fun addMemberInGitr(args: GitrAddMembersListRequestDTO, token: Cookie): GitrEntityResponseDTO?{
        val user = cookieValidator.returnUserIfAuthTokenValid(token)?:return null
        var entity = gitrRepository.findById(args.repositoryId).get()
        if(entity.gitrOwner==null) return null

        if (user.id!=entity.gitrOwner!!.id) return null


        for (e in args.gitrMembersNames){
            if(!entity.gitrMembersNames.contains(e)) entity.gitrMembersNames.add(e)
        }
        return GitrEntityResponseDTO(gitrRepository.update(entity))
    }

    fun getMemberListFromGitr(id: UUID,token: Cookie): HttpResponse<Any> {

        val userRequester = cookieValidator.returnUserIfAuthTokenValid(token)
        if (userRequester==null)
            return HttpResponse.unauthorized()
        val gitr = gitrRepository.findById(id)

        if (gitr.isEmpty) return HttpResponse.badRequest()
        if(gitr.get().gitrOwner!!.id!=userRequester.id)
            return HttpResponse.badRequest()


        return HttpResponse.ok(mapOf("repos" to gitr.get()))
    }
}