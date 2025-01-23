package com.brayan.renovar.models

import java.time.LocalDateTime
import java.util.UUID

data class AddressModel(
    val id: UUID? = null,
    val street: String? = null,
    val number: String? = null,
    val neighborhood: String? = null,
    val city: String,
    val state: String,
    val zipCode: String,
    val complement: String? = null,
    val creationDate: LocalDateTime? = null,
    val updateDate: LocalDateTime? = null
)
