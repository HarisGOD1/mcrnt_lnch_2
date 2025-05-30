package ru.thegod.security

import io.micronaut.core.annotation.NonNull
import io.micronaut.data.annotation.Join
import io.micronaut.data.annotation.Query
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository
import ru.thegod.gitr.GitrEntity
import java.util.*


@JdbcRepository(dialect= Dialect.POSTGRES)
@Join(value = "ownedRepositories", type = Join.Type.LEFT_FETCH)
interface UserRepository: CrudRepository<User, UUID>{

    fun findByUsername(username: String?): User?


    @Join(value = "ownedRepositories", type = Join.Type.LEFT_FETCH)
    fun getById(id: UUID): Optional<User>


}