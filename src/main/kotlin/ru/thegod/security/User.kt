package ru.thegod.security

import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.MappedProperty
import io.micronaut.data.annotation.Relation
import jakarta.persistence.*
import ru.thegod.gitr.GitrEntity
import java.util.*

@MappedEntity
@Table(name="user_table")
class User(@Id
           @GeneratedValue
           val id: UUID?,
           @MappedProperty("username")
           @Column(unique=true)
           val username:String,
           @MappedProperty("password_hash")
           val passwordHash:String,
//           @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
           @Relation(Relation.Kind.ONE_TO_MANY,mappedBy = "gitrOwner", cascade = arrayOf(Relation.Cascade.ALL))
           @MappedProperty("ownedrepositories")
           val ownedRepositories:MutableList<GitrEntity> = mutableListOf()

           ) {
    override fun toString(): String {
        return "$id\n$username $ownedRepositories"
    }

    override fun equals(other: Any?): Boolean {
        if(this===other) return true
        if(other !is User) return false
        if(
            ((id==null) or (other.id==null) or (id==other.id ))
            &&
            username==other.username
            &&
            passwordHash==other.passwordHash
            &&
            (
                    (ownedRepositories==other.ownedRepositories)
                    or
                    ((ownedRepositories.size==other.ownedRepositories.size)
                            &&
                            (ownedRepositories.containsAll(other.ownedRepositories)))
                    )
            )
        return true
        else return false
    }

}