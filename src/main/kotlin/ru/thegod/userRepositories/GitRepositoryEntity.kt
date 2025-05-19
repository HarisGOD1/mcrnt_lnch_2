package ru.thegod.userRepositories

import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.MappedProperty
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table


@MappedEntity
@Table(name="git_repository_list")
data class GitRepositoryEntity(@Id @GeneratedValue val id: Long?,
                               @MappedProperty("gitrepository") val gitRepositoryName: String,
                               @MappedProperty("gitownername") val gitOwnerName: String,
                               @MappedProperty("publicity") var publicity: Boolean,
                               @MappedProperty("membersnames") var membersNames: String, // TO-DO: make it psql Array
                               @MappedProperty("gitrepositorydescription") var repositoryDescription: String?,
                               @MappedProperty("lastcommitgenerated") var lastCommitGenerated: String?
                               ) {

}