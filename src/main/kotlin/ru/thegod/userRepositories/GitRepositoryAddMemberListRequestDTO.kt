package ru.thegod.userRepositories

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Introspected
@Serdeable
data class GitRepositoryAddMemberListRequestDTO
    (val repositoryId: Long,val memberNames: List<String>)
