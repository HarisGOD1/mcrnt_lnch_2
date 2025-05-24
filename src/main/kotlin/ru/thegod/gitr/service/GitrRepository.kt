package ru.thegod.gitr.service

import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository
import ru.thegod.gitr.GitrEntity
import java.util.*

@JdbcRepository(dialect=Dialect.POSTGRES)
interface GitrRepository: CrudRepository<GitrEntity, UUID> {

}
