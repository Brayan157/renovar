package com.brayan.renovar.api.response

import com.brayan.renovar.enum.EmployeeStatus
import com.brayan.renovar.models.AddressModel
import java.time.LocalDate
import java.util.UUID

data class EmployeeResponse(
    val id: UUID,
    val name: String,
    val registration: Int,
    val cpf: String,
    val birthDate: LocalDate,
    val phones: String,
    val generalRegistration: String,
    val hourlyRate: Double,
    val function: String,
    val status: EmployeeStatus,
    val addressModel: AddressModel
)
