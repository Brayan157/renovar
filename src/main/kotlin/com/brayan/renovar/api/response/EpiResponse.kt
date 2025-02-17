package com.brayan.renovar.api.response

import com.brayan.renovar.models.EmployeeEPIModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class EpiResponse(
    val id: UUID,
    val name:String,
    val approvalCertificate: String? = null,
    val quantity: Int,
    val unitValue: Double? = null,
    val manufacturingDate: LocalDate,
    val expirationDate: LocalDate? = null,
    val tag:String? = null,
    val lot:String? = null
)
