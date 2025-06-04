package ru.thegod.security.user.models

import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.MappedProperty
import io.micronaut.data.model.DataType
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Transient
import ru.thegod.security.authentication.services.PasswordEncryptService.md5
import ru.thegod.security.user.roles.UserRole.*
import ru.thegod.security.user.roles.UserRole
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
           @MappedProperty("owned_repositories_id_list")
           @ElementCollection(targetClass = UUID::class)
            val ownedRepositoriesIdList:MutableList<UUID> = mutableListOf(),
           @ElementCollection(targetClass = UserRole::class, fetch = FetchType.EAGER)
           @Enumerated(EnumType.STRING)
           var roles: List<UserRole> = listOf()

//           @Transient
//            val roles: MutableList<UserRole> = mutableListOf()
           ) {

//      default user creation
    constructor(username:String,passwordHash: String):
            this(null,username,passwordHash,
                mutableListOf(),
                mutableListOf(DEFAULT_USER)
            )

    override fun toString(): String {
        return "$id\n|$username|$ownedRepositoriesIdList|$roles|"
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
                    (ownedRepositoriesIdList==other.ownedRepositoriesIdList)
                    or
                    ((ownedRepositoriesIdList.size==other.ownedRepositoriesIdList.size)
                            &&
                            (ownedRepositoriesIdList.containsAll(other.ownedRepositoriesIdList)))
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