package ru.thegod.userRepositories

import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.MappedProperty
import io.micronaut.serde.annotation.Serdeable


@Introspected
@Serdeable
data class GitRepositoryEntityResponseDTO(val id:Long?,val gitRepositoryName: String, val gitOwnerName: String,
                                         val publicity: Boolean,val membersNames:MutableList<String>,
                                          val repositoryDescription:String?,val lastCommitGenerated: String?)
{
    constructor(): this(null,"","",false, mutableListOf(),
        null,null)
    fun toRepositoryEntity(): GitRepositoryEntity{
        return GitRepositoryEntity(this)
    }
}
