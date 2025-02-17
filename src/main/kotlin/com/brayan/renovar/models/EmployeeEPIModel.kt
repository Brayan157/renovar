package com.brayan.renovar.models

import com.brayan.renovar.enum.EPIStatus
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class EmployeeEPIModel(
    val employeeId: UUID,
    val epiId: UUID,
    val quantity: Int,
    val deliveryDate: LocalDate,
    val returnDate: LocalDate? = null,
    val epiStatus: EPIStatus,
    val reason: String,
    val creationDate: LocalDateTime? = null,
    val updateDate: LocalDateTime? = null,
    val creationDateId: UUID
)
