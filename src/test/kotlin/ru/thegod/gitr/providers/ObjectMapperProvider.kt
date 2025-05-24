package ru.thegod.gitr.providers

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.inject.Singleton

@Singleton
object ObjectMapperProvider {
    val mapper: ObjectMapper by lazy {
        ObjectMapper()
//            .registerModule(com.fasterxml.jackson.module.kotlin.KotlinModule())
//            .findAndRegisterModules() // optional: picks up all registered Jackson modules
    }
}