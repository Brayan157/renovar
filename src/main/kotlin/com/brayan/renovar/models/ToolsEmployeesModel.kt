package com.brayan.renovar.models

import java.time.LocalDateTime
import java.util.UUID

data class ToolsEmployeesModel(
    val employeeId: UUID,
    val toolId: UUID,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime? = null,
    val quantity: Int?,
    val creationDate: LocalDateTime? = null,
    val updateData: LocalDateTime? = null
)
