package com.brayan.renovar.api.response

import com.brayan.renovar.enum.EPIStatus
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class EmployeeEpiResponse(
    val employee: EmployeeResponse,
    val epi: EpiResponse,
    val creationDateId: UUID,
    val quantity: Int,
    val deliveryDate: LocalDate,
    val returnDate: LocalDate? = null,
    val epiStatus: EPIStatus,
    val reason: String
)
