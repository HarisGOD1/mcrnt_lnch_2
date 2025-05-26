package ru.thegod.security

import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.MappedProperty
import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@MappedEntity
@Table(name="user_table1")
class User(@Id
           @GeneratedValue
           val id: UUID?,
           @MappedProperty("username")
           @Column(unique=true)
           val username:String,
           @MappedProperty("password_hash")
           val passwordHash:String) {

}