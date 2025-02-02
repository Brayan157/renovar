package com.brayan.renovar.api.request

import java.time.LocalDateTime
import java.util.UUID

data class AddressUpdateRequest(
    val id: UUID,
    val street: String? = null,
    val number: String? = null,
    val neighborhood: String? = null,
    val city: String? = null,
    val state: String? = null,
    val zipCode: String? = null,
    val complement: String? = null,
    val creationDate: LocalDateTime? = null,
    val updateDate: LocalDateTime? = null
)
