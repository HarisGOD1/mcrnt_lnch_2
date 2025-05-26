package ru.thegod.security

import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.MappedProperty
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
           @OneToMany(mappedBy = "gitrOwner",fetch=FetchType.LAZY, cascade = [CascadeType.ALL])
           @MappedProperty("ownedrepositories")
           val ownedRepositories:MutableList<GitrEntity> = mutableListOf()

           ) {

}