package com.brayan.renovar.api.response

import com.brayan.renovar.database.entities.CreationDate
import java.time.LocalDate

data class EmployeeWorkResponse(
    val workResponse: WorkResponse,
    val employeeResponse: EmployeeResponse,
    val startDate: LocalDate,
    val endDate: LocalDate? = null,
    val creationDate: CreationDate
)
