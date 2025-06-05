package ru.thegod.security.user.repositories

import io.micronaut.data.annotation.Join
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository
import ru.thegod.security.user.models.User
import java.util.Optional
import java.util.UUID

@JdbcRepository(dialect= Dialect.POSTGRES)
@Join(value = "ownedRepositoriesList", type = Join.Type.LEFT_FETCH)
interface UserRepository: CrudRepository<User, UUID> {

    fun findByUsername(username: String?): User?

    @Join(value = "ownedRepositoriesList", type = Join.Type.LEFT_FETCH)
    fun getById(id: UUID): Optional<User>


}