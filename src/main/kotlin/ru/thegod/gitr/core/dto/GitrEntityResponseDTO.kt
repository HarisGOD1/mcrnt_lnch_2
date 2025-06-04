package ru.thegod.gitr.core.dto

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import ru.thegod.gitr.GitrEntity
import java.util.*


@Introspected
@Serdeable
data class GitrEntityResponseDTO(val id: UUID?, val gitrName: String,
                                 val gitrOwnerId: UUID?, val publicity: Boolean,
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
            gitrEntity.gitrOwnerId,gitrEntity.publicity,
            gitrEntity.gitrMembersNames,
        gitrEntity.gitrDescription,
        gitrEntity.gitrCommitGenerated)

}
