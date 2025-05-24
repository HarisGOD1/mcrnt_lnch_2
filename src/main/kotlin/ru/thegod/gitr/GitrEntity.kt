package ru.thegod.gitr

import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.MappedProperty
import io.micronaut.data.model.DataType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import ru.thegod.gitr.DTO.GitrEntityRequestDTO
import ru.thegod.gitr.DTO.GitrEntityResponseDTO
import java.util.*


@MappedEntity
@Table(name="git_repository_list")
data class GitrEntity(@Id @GeneratedValue val id: UUID? = null,
                      @MappedProperty("gitrepository") val gitrName: String,
                      @MappedProperty("gitownername") val gitrOwnerName: String,
                      @MappedProperty("publicity") var publicity: Boolean,
                      @MappedProperty("membersnames", type= DataType.STRING_ARRAY) var gitrMembersNames: MutableList<String>, // TO-DO: make it psql Array
                      @MappedProperty("gitrepositorydescription") var gitrDescription: String?,
                               // if this nullable will be null before saving,
                            // then after get from db this will be empty String
                      @MappedProperty("lastcommitgenerated") var gitrCommitGenerated: String?
                               ) {
    constructor(gitRepositoryName: String, gitOwnerName: String, publicity: Boolean, repositoryDescription: String?) :
            this(null,gitRepositoryName,gitOwnerName, publicity,
                mutableListOf(),repositoryDescription,null)

    constructor(requestDTO: GitrEntityRequestDTO) :
            this(requestDTO.gitrName,requestDTO.gitrOwnerName,
                requestDTO.publicity,requestDTO.gitrDescription)

    constructor(responseDTO: GitrEntityResponseDTO) :
            this(responseDTO.id, responseDTO.gitrName,responseDTO.gitrOwnerName,
                responseDTO.publicity,responseDTO.gitrMembersNames,
                responseDTO.gitrDescription,responseDTO.gitrCommitGenerated)



    fun toResponseDTO(): GitrEntityResponseDTO {
        return GitrEntityResponseDTO(this.id,this.gitrName,this.gitrOwnerName,
            this.publicity,this.gitrMembersNames,this.gitrDescription,this.gitrCommitGenerated)
    }



    override fun equals(other: Any?):Boolean{
        if (this === other) return true
        if (other !is GitrEntity) return false
        // Compares properties for structural equality
        return (
                    ((this.id == other.id) or (this.id==null && other.id != null)) /* for the case where we wanna check with pre init data*/
                    &&
                    this.gitrName == other.gitrName
                    &&
                    this.gitrOwnerName == other.gitrOwnerName
                    &&
                    this.publicity == other.publicity
                    &&
                    ((this.gitrDescription.isNullOrEmpty() == other.gitrDescription.isNullOrEmpty()) or
                            (this.gitrDescription == other.gitrDescription))
                    &&
                    ((this.gitrCommitGenerated.isNullOrEmpty() == other.gitrCommitGenerated.isNullOrEmpty()) or
                            (this.gitrCommitGenerated == other.gitrCommitGenerated))
                    &&
                    (if(this.gitrMembersNames === other.gitrMembersNames) true
                        else (
                            (this.gitrMembersNames.size == other.gitrMembersNames.size)
                            &&
                            (this.gitrMembersNames.containsAll(other.gitrMembersNames))
                        )
                    )
                )
    }



}