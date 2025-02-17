package com.brayan.renovar.models

import java.time.LocalDateTime
import java.util.UUID

data class CreationDateModel(
    val id: UUID,
    val creationDate: LocalDateTime? = null,
)
