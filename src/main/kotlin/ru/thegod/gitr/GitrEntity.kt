package ru.thegod.gitr

import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.MappedProperty
import io.micronaut.data.annotation.Relation
import io.micronaut.data.model.DataType
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.*
import ru.thegod.security.user.models.User
import java.util.*


@Serdeable
@MappedEntity
@Table(name="git_repository_list")
data class GitrEntity(
    @Id
                      @GeneratedValue
                      val id: UUID? = null,
    @MappedProperty("gitr_name")
                      val gitrName: String,
    @MappedProperty("gitr_owner_id")
                      @Relation(value = Relation.Kind.MANY_TO_ONE)
                      val gitrOwner: User?,// owner user.class -> uuid.class bc of "@Join is bad"
    @MappedProperty("publicity")
                      var publicity: Boolean,
    @MappedProperty("membersnames", type= DataType.STRING_ARRAY)
                      var gitrMembersNames: MutableList<String>,
    @MappedProperty("gitrepositorydescription")
                      var gitrDescription: String?,
    @MappedProperty("lastcommitgenerated")
                      var gitrCommitGenerated: String?
                               ) {
    // minimal constructor
   //
    constructor(gitRepositoryName: String,
                gitrOwner:User,
                publicity: Boolean,
                repositoryDescription: String?) :

            this(null,
                gitRepositoryName,
                gitrOwner,
                publicity,
                mutableListOf(),
                repositoryDescription,
                null)



    override fun equals(other: Any?):Boolean{
        if (this === other) return true
        if (other !is GitrEntity) return false
        // Compares properties for structural equality
        return (
                    ((this.id == other.id) or (this.id==null && other.id != null)) /* for the case where we wanna check with pre init data*/
                    &&
                    this.gitrName == other.gitrName
//                    &&
//                    this.id==other.id             // WTF THIS SH..
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


    override fun toString(): String {
        return "GITR ENTITY: id:$id\n|name:$gitrName|description:$gitrDescription|genCommit:$gitrCommitGenerated|"+
               "ownerId:${  if (gitrOwner==null)"null" else gitrOwner.id}|\n"
    }
}