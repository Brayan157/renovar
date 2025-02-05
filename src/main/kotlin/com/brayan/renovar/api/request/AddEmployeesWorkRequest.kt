package com.brayan.renovar.api.request

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class AddEmployeesWorkRequest(
    val workId: UUID,
    val employees: List<UUID>,
    val startDate: LocalDate
)
