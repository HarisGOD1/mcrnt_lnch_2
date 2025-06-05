package ru.thegod.gitr.core.dto

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import ru.thegod.gitr.GitrEntity

//@JvmRecord
@Introspected
@Serdeable
data class GitrEntityRequestDTO(val gitrName: String,
                                val publicity: Boolean, val gitrDescription:String){

}

