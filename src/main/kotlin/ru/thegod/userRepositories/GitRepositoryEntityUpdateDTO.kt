package ru.thegod.userRepositories

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Introspected
@Serdeable
data class GitRepositoryEntityUpdateDTO(val id:Long, val gitRepositoryName: String, val gitOwnerName: String,
                                        val publicity: Boolean, val membersNames:List<String>,
                                        val repositoryDescription:String?, val lastCommitGenerated: String?)