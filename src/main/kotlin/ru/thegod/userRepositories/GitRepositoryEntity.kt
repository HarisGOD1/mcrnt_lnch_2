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
                               // if this nullable will be null before saving,
                            // then after get from db this will be empty String
                               @MappedProperty("lastcommitgenerated") var lastCommitGenerated: String?
                               ) {
    constructor(gitRepositoryName: String, gitOwnerName: String, publicity: Boolean, repositoryDescription: String?) :
            this(null,gitRepositoryName,gitOwnerName, publicity,
                mutableListOf(),repositoryDescription,null)

    constructor(requestDTO: GitRepositoryEntityRequestDTO) :
            this(requestDTO.gitRepositoryName,requestDTO.gitOwnerName,
                requestDTO.publicity,requestDTO.repositoryDescription)

    constructor(responseDTO: GitRepositoryEntityResponseDTO) :
            this(responseDTO.id, responseDTO.gitRepositoryName,responseDTO.gitOwnerName,
                responseDTO.publicity,responseDTO.membersNames,
                responseDTO.repositoryDescription,responseDTO.lastCommitGenerated)



    fun toResponseDTO():GitRepositoryEntityResponseDTO{
        return GitRepositoryEntityResponseDTO(this.id,this.gitRepositoryName,this.gitOwnerName,
            this.publicity,this.membersNames,this.repositoryDescription,this.lastCommitGenerated)
    }



    override fun equals(other: Any?):Boolean{
        if (this === other) return true
        if (other !is GitRepositoryEntity) return false
        // Compares properties for structural equality
        return (((this.id == other.id) or (this.id==null && other.id != null/* for the case where we wanna check with pre init data*/)
                    ) &&
                this.gitRepositoryName == other.gitRepositoryName &&
                this.gitOwnerName == other.gitOwnerName &&
                this.publicity == other.publicity &&
                ((this.repositoryDescription.isNullOrEmpty() == other.repositoryDescription.isNullOrEmpty()) or
                        (this.repositoryDescription == other.repositoryDescription)) &&
                ((this.lastCommitGenerated.isNullOrEmpty() == other.lastCommitGenerated.isNullOrEmpty()) or
                        (this.lastCommitGenerated == other.lastCommitGenerated))&&
                (if(this.membersNames === other.membersNames) true
                    else ((this.membersNames.size == other.membersNames.size) &&
                        (this.membersNames.containsAll(other.membersNames)))
                        )
                )
    }



}