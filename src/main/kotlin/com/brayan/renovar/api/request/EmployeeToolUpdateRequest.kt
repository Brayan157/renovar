package com.brayan.renovar.api.request

import com.brayan.renovar.database.entities.ToolsEmployeesKey
import com.brayan.renovar.enum.ToolEmployee
import java.time.LocalDate

data class EmployeeToolUpdateRequest (
    val id: ToolsEmployeesKey,
    val endDate: LocalDate,
    val status: ToolEmployee = ToolEmployee.DEVOLVIDO,
)
