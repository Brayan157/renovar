package com.brayan.renovar.api.response

import com.brayan.renovar.enum.ToolEmployee
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID


data class EmployeeToolResponse (
    val employee: EmployeeResponse,
    val tool: ToolResponse,
    val startDate: LocalDate,
    val endDate: LocalDate? = null,
    val quantity: Int,
    val status: ToolEmployee,
    val creationDate: LocalDateTime? = null,
    val updateDate: LocalDateTime? = null,
    val creationDateId: UUID
)
