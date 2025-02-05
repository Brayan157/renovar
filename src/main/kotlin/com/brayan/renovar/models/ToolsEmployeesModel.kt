package com.brayan.renovar.models

import com.brayan.renovar.enum.ToolEmployee
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class ToolsEmployeesModel(
    val employeeId: UUID,
    val toolId: UUID,
    val startDate: LocalDate,
    val endDate: LocalDate? = null,
    val quantity: Int?,
    val creationDate: LocalDateTime? = null,
    val updateDate: LocalDateTime? = null,
    val status: ToolEmployee
)
