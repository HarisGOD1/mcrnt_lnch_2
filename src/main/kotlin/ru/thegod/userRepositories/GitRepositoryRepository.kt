package ru.thegod.userRepositories

import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import jakarta.persistence.Table

@JdbcRepository(dialect=Dialect.POSTGRES)
interface GitRepositoryRepository {

}