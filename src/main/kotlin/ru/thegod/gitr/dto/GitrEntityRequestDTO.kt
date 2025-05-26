package ru.thegod.gitr.dto

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import ru.thegod.gitr.GitrEntity

//@JvmRecord
@Introspected
@Serdeable
data class GitrEntityRequestDTO(val gitrName: String, val gitrOwnerName: String,
                                val publicity: Boolean, val gitrDescription:String?){
    constructor(gitrEntity:GitrEntity) : this(gitrEntity.gitrName,gitrEntity.gitrOwnerName,
        gitrEntity.publicity,gitrEntity.gitrDescription)

}

