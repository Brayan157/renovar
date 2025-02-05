package com.brayan.renovar.api.request

import com.brayan.renovar.enum.ToolEmployee
import java.time.LocalDate
import java.util.UUID

data class AddToolsForEmployeeRequest (
    val employeeId: UUID,
    val tools: Map<UUID, Long>,
    val startDate: LocalDate,
    val status: ToolEmployee = ToolEmployee.ENTREGUE
)
