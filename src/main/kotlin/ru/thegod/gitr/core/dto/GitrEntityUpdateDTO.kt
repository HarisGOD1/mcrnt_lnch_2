package ru.thegod.gitr.core.dto

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import java.util.*

@Introspected
@Serdeable
data class GitrEntityUpdateDTO(val id: UUID?, val gitrName: String, val gitrOwnerName: String,
                               val publicity: Boolean, val gitrMembersNames:List<String>,
                               val gitrDescription:String?, val gitrCommitGenerated: String?)