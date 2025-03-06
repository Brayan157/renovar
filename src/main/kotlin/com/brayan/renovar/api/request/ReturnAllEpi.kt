package com.brayan.renovar.api.request

import com.brayan.renovar.models.EmployeeModel
import java.time.LocalDate
import java.util.UUID

data class ReturnAllEpi(
    val employeeId: UUID,
    val returnDate: LocalDate
)
