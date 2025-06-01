package ru.thegod.gitr.dto

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import ru.thegod.gitr.GitrEntity
import ru.thegod.security.User

//@JvmRecord
@Introspected
@Serdeable
data class GitrEntityRequestDTO(val gitrName: String, val gitrOwner: User,
                                val publicity: Boolean, val gitrDescription:String){
    constructor(gitrEntity:GitrEntity) : this(gitrEntity.gitrName,
        gitrEntity.gitrOwner!!, gitrEntity.publicity,gitrEntity.gitrDescription!!)

}

