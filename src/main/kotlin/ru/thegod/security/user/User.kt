package ru.thegod.security.user

import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.MappedProperty
import io.micronaut.data.annotation.Relation
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Transient
import ru.thegod.gitr.GitrEntity
import ru.thegod.security.authentication.services.PasswordEncryptService.md5
import java.util.UUID

@Serdeable
@Introspected
@MappedEntity
@Table(name="user_table")
class User(@Id
           @GeneratedValue
           val id: UUID?,
           @MappedProperty("username")
           @Column(unique=true)
           val username:String,
           @MappedProperty("password_hash")
           var passwordHash:String,
           // to-do replace with something another cause of N+1 / N^N problem
           @MappedProperty("ownedrepositories")
           @Relation(value = Relation.Kind.ONE_TO_MANY, mappedBy = "gitrOwner", cascade = arrayOf(Relation.Cascade.ALL))
           val ownedRepositories:MutableList<GitrEntity> = mutableListOf()

           ) {
    constructor(id: UUID, username: String, passwordHash: String) :
            this(id,username,passwordHash, mutableListOf())
    constructor(username:String,passwordHash: String):
            this(null,username,passwordHash, mutableListOf())

    override fun toString(): String {
        return "$id\n|$username|$ownedRepositories|"
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

    @Transient
    fun securityHash(): String{
        return (username+passwordHash).md5()
    }

}