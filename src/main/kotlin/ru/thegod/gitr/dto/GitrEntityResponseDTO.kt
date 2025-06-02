package ru.thegod.gitr.dto

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import ru.thegod.gitr.GitrEntity
import ru.thegod.security.User
import java.util.*


@Introspected
@Serdeable
data class GitrEntityResponseDTO(val id: UUID?, val gitrName: String, val gitrOwnerName: String,
                                 val publicity: Boolean, val gitrMembersNames:MutableList<String>,
                                 val gitrDescription:String?, val gitrCommitGenerated: String?)
{
    constructor(): this(null,"","",false, mutableListOf(),
        null,null)
    fun toRepositoryEntity(): GitrEntity {
        return GitrEntity(this)
    }
}
