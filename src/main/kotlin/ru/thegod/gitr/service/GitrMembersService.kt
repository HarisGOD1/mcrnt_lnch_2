package ru.thegod.gitr.service

import io.micronaut.http.HttpResponse
import io.micronaut.http.cookie.Cookie
import jakarta.inject.Singleton
import ru.thegod.gitr.GitrEntity
import ru.thegod.gitr.dto.GitrAddMembersListRequestDTO
import ru.thegod.gitr.dto.GitrEntityResponseDTO
import ru.thegod.security.cookie.CookieValidator
import java.util.*

@Singleton
class GitrMembersService(private val gitrRepository: GitrRepository,
                         private val cookieValidator: CookieValidator
) {
    fun addMemberInGitr(args: GitrAddMembersListRequestDTO, token: Cookie): GitrEntityResponseDTO?{
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

    fun getMemberListFromGitr(id: UUID,token: Cookie): HttpResponse<Any> {

        val user = cookieValidator.returnUserIfAuthTokenValid(token)
        if (user==null)
            return HttpResponse.unauthorized()

        val gitr = gitrRepository.findById(id)?:return HttpResponse.badRequest()
//        println(gitr)
        if (gitr.isEmpty) return HttpResponse.badRequest()
        if(gitr.get().gitrOwnerName!=user.username)
            return HttpResponse.badRequest()


        return HttpResponse.ok(mapOf("repos" to gitr.get()))
    }
}