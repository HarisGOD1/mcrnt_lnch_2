package ru.thegod.security

import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository
import java.util.*


@JdbcRepository(dialect= Dialect.POSTGRES)
interface UserRepository: CrudRepository<User, UUID>{

    fun findByUsername(username: String?): User?
}