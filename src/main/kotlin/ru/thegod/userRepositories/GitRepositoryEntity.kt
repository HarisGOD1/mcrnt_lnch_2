package ru.thegod.userRepositories

import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.MappedProperty
import io.micronaut.data.model.DataType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table


@MappedEntity
@Table(name="git_repository_list")
data class GitRepositoryEntity(@Id @GeneratedValue val id: Long?,
                               @MappedProperty("gitrepository") val gitRepositoryName: String,
                               @MappedProperty("gitownername") val gitOwnerName: String,
                               @MappedProperty("publicity") var publicity: Boolean,
                               @MappedProperty("membersnames", type= DataType.STRING_ARRAY) var membersNames: MutableList<String>, // TO-DO: make it psql Array
                               @MappedProperty("gitrepositorydescription") var repositoryDescription: String?,
                               @MappedProperty("lastcommitgenerated") var lastCommitGenerated: String?
                               ) {
    constructor(gitRepositoryName: String, gitOwnerName: String, publicity: Boolean, repositoryDescription: String?) :
            this(null,gitRepositoryName,gitOwnerName, publicity,
                mutableListOf(),repositoryDescription,"")
    fun toResponseDTO():GitRepositoryEntityResponseDTO{
        return GitRepositoryEntityResponseDTO(this.id,this.gitRepositoryName,this.gitOwnerName,
            this.publicity,this.membersNames,this.repositoryDescription,this.lastCommitGenerated)
    }

}