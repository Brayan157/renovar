package com.brayan.renovar.api.request

import com.brayan.renovar.enum.ToolEmployee
import java.time.LocalDate
import java.util.UUID

data class EmployeeToolRequest (
    val employeeId: UUID,
    val toolId: UUID,
    val startDate: LocalDate,
    val quantity: Int,
    val status: ToolEmployee = ToolEmployee.ENTREGUE,
)
