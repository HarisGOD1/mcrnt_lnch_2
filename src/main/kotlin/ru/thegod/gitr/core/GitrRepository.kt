package ru.thegod.gitr.core

import io.micronaut.data.annotation.Join
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository
import ru.thegod.gitr.GitrEntity
import java.util.*


//@Join(value = "gitrOwner", type = Join.Type.LEFT_FETCH)
@JdbcRepository(dialect=Dialect.POSTGRES)
interface GitrRepository: CrudRepository<GitrEntity, UUID> {

//    @Join(value = "gitrOwner", type = Join.Type.LEFT_FETCH)
    fun getById(id:UUID):Optional<GitrEntity>
}
