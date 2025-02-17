package com.brayan.renovar.api.request

import java.time.LocalDate
import java.util.UUID

data class AddEpiToEmployeeRequest (
    val employeeId: UUID,
    val epiId: UUID,
    val quantity: Int,
    val deliveryDate: LocalDate,
    val reason: String
)
