package com.brayan.renovar.models

import java.time.LocalDateTime
import java.util.UUID

data class EmployeeWorkModel(
    val employeeId: UUID,
    val workId: UUID,
    val startDate: LocalDateTime? = null,
    val endDate: LocalDateTime? = null,
)
