package ru.thegod.gitr

import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.MappedProperty
import io.micronaut.data.model.DataType
import jakarta.persistence.*
import ru.thegod.gitr.dto.GitrEntityRequestDTO
import ru.thegod.gitr.dto.GitrEntityResponseDTO
import ru.thegod.security.User
import java.util.*


@MappedEntity
@Table(name="git_repository_list")
data class GitrEntity(@Id @GeneratedValue val id: UUID? = null,
                      @MappedProperty("gitrepository") val gitrName: String,
                      @MappedProperty("gitownername") val gitrOwnerName: String,

                      @ManyToOne(fetch = FetchType.LAZY)
                      @JoinColumn(name="gitrowner_id",referencedColumnName="user_table.id")
                      val gitrOwner: User,// in db this is field, containing id(uuid) that refere to some User

                      @MappedProperty("publicity") var publicity: Boolean,
                      @MappedProperty("membersnames", type= DataType.STRING_ARRAY) var gitrMembersNames: MutableList<String>, // TO-DO: make it psql Array
                      @MappedProperty("gitrepositorydescription") var gitrDescription: String?,
                               // if this nullable will be null before saving,
                            // then after get from db this will be empty String
                      @MappedProperty("lastcommitgenerated") var gitrCommitGenerated: String?
                               ) {
    constructor(gitRepositoryName: String, gitOwnerName: String,gitrOwner: User, publicity: Boolean, repositoryDescription: String?) :
            this(null,gitRepositoryName,gitOwnerName,gitrOwner, publicity,
                mutableListOf(),repositoryDescription,null)

    constructor(requestDTO: GitrEntityRequestDTO) :
            this(requestDTO.gitrName,requestDTO.gitrOwnerName,requestDTO.gitrOwner!!,
                requestDTO.publicity,requestDTO.gitrDescription)

    constructor(responseDTO: GitrEntityResponseDTO) :
            this(responseDTO.id, responseDTO.gitrName,responseDTO.gitrOwnerName,responseDTO.gitrOwner!!,
                responseDTO.publicity,responseDTO.gitrMembersNames,
                responseDTO.gitrDescription,responseDTO.gitrCommitGenerated)



    fun toResponseDTO(): GitrEntityResponseDTO {
        return GitrEntityResponseDTO(this.id,this.gitrName,this.gitrOwnerName,this.gitrOwner,
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
                    this.gitrOwner==other.gitrOwner
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