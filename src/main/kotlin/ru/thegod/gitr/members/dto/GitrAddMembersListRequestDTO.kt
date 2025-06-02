package ru.thegod.gitr.members.dto

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import java.util.*

@Introspected
@Serdeable
data class GitrAddMembersListRequestDTO
    (val repositoryId: UUID, val gitrMembersNames: List<String>)
