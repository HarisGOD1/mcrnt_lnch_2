package ru.thegod.gitr.core.dto

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import ru.thegod.gitr.GitrEntity

@Introspected
@Serdeable
class GitrEntityCreationRequestDTO(val gitrName:String,
                                    val gitrDescription: String,
                                    val publicity: Boolean) {
}
