package com.brayan.renovar.models

import java.time.LocalDateTime
import java.util.UUID

data class FunctionModel(
    val id: UUID? = null,
    val function: String,
    val description: String,
    val creationDate: LocalDateTime? = null,
    val updateDate: LocalDateTime? =  null
)