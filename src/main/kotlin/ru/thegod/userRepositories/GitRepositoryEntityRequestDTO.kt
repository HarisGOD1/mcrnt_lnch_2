package ru.thegod.userRepositories

import io.micronaut.serde.annotation.Serdeable

@JvmRecord
@Serdeable
data class GitRepositoryEntityRequestDTO(val gitRepositoryName: String, val gitOwnerName: String,
                                         val publicity: Boolean, val repositoryDescription:String?)
//null,gitRepositoryName,gitOwnerName, publicity,
//                mutableListOf(),repositoryDescription,""