package ru.thegod.gitr.core.dto

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import ru.thegod.gitr.GitrEntity
import ru.thegod.security.user.models.User
import java.util.*


@Introspected
@Serdeable
data class GitrEntityResponseDTO(val id: UUID?, val gitrName: String,
                                 val gitrOwner: User?, val publicity: Boolean,
                                 val gitrMembersNames:MutableList<String>,
                                 val gitrDescription:String?, val gitrCommitGenerated: String?)
{

    // empty constructor
    constructor(): this(null,
        "",
        null,
        false,
        mutableListOf(),
        null,
        null)

    // generate resp from
    constructor(gitrEntity: GitrEntity) : this(gitrEntity.id,gitrEntity.gitrName,
            gitrEntity.gitrOwner,gitrEntity.publicity,
            gitrEntity.gitrMembersNames,
        gitrEntity.gitrDescription,
        gitrEntity.gitrCommitGenerated)

}
