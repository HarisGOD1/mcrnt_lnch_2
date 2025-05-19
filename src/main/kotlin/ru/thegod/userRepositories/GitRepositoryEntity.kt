package ru.thegod.userRepositories

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.MappedProperty
import jakarta.persistence.Table

@MappedEntity
@Table(name="git_repository_list")
data class GitRepositoryEntity(@Id @GeneratedValue val id: Long?,
                               @MappedProperty("gitRepository") val gitRepositoryName: String,
                               @MappedProperty("gitOwnerName") val gitOwnerName: String,
                               @MappedProperty("publicity") var publicity: Boolean,
                               @MappedProperty("membersNames") var membersNames: MutableList<String>,
                               @MappedProperty("gitRepositoryDescription") var repositoryDescription: String?,
                               @MappedProperty("lastCommitGenerated") var lastCommitGenerated: String?
                               ) {

}