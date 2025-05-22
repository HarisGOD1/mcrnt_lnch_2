package ru.thegod.userRepositories

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

//@JvmRecord
@Introspected
@Serdeable
data class GitRepositoryEntityRequestDTO(val gitRepositoryName: String, val gitOwnerName: String,
                                         val publicity: Boolean, val repositoryDescription:String?)
