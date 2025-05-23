package ru.thegod.userRepositories

import io.micronaut.context.annotation.Parameter
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedProperty
import io.micronaut.data.annotation.QueryHint
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository

@JdbcRepository(dialect=Dialect.POSTGRES)
interface GitRRepository: CrudRepository<GitRepositoryEntity,Long> {
//    @QueryHint(name = "jakarta.persistence.FlushModeType", value = "AUTO")
//    fun updateMembersNames(@Id id: Long?, @MappedProperty("membersnames") members: MutableList<String>)
}