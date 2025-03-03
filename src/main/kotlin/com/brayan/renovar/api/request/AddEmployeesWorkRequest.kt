package com.brayan.renovar.api.request

import com.brayan.renovar.models.EmployeeModel
import com.brayan.renovar.models.WorkModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class AddEmployeesWorkRequest(
    val workId: UUID,
    val employeeId: UUID,
    val startDate: LocalDate
)
