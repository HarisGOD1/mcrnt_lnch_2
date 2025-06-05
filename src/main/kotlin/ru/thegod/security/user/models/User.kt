package ru.thegod.security.user.models

import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.MappedProperty
import io.micronaut.data.annotation.Relation
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Transient
import ru.thegod.gitr.GitrEntity
import ru.thegod.security.authentication.services.PasswordEncryptService.md5
import ru.thegod.security.user.roles.UserRole.*
import ru.thegod.security.user.roles.UserRole
import ru.thegod.security.user.roles.UserRolesConverter
import java.util.UUID

@Serdeable
@Introspected
@MappedEntity
@Table(name="user_table")
class User(
    @Id
            @GeneratedValue
            var id: UUID?,
    @MappedProperty("username")
            @Column(unique=true)
            var username:String,
    @MappedProperty("password_hash")
            var passwordHash:String,
    @MappedProperty("owned_repositories_id_list")
            @Relation(value = Relation.Kind.ONE_TO_MANY, mappedBy = "gitrOwner", cascade = arrayOf(Relation.Cascade.ALL))
//            Add commentMore actions
            var ownedRepositoriesList:MutableList<GitrEntity> = mutableListOf(),
    @Convert(converter = UserRolesConverter::class)
           @MappedProperty("role_number")
           var roles: MutableSet<UserRole> = mutableSetOf()
           ) {

//      default user creation
    constructor(username:String,passwordHash: String):
            this(null,username,passwordHash,
                mutableListOf(),
                mutableSetOf(DEFAULT_USER)
            )
    constructor() : this(null,"","",mutableListOf(),mutableSetOf())
    override fun toString(): String {
        return "USER ENTITY: $id\n|$username|$ownedRepositoriesList|$roles|\n"
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
            ((roles==other.roles)
                    or
                    (
                    (roles.size==other.roles.size)
                            &&
                    roles.containsAll(other.roles)
                    )
            )
            &&
            (
                    (ownedRepositoriesList==other.ownedRepositoriesList)
                    or
                    ((ownedRepositoriesList.size==other.ownedRepositoriesList.size)
                            &&
                            (ownedRepositoriesList.containsAll(other.ownedRepositoriesList)))
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