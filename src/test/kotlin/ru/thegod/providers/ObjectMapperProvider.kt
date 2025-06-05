package ru.thegod.providers

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.inject.Singleton

@Singleton
object ObjectMapperProvider {
    val mapper: ObjectMapper by lazy {
        ObjectMapper()
    }
}