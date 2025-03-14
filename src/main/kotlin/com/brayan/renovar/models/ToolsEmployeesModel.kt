package com.brayan.renovar.models

import com.brayan.renovar.api.response.EmployeeResponse
import com.brayan.renovar.api.response.EmployeeToolResponse
import com.brayan.renovar.api.response.ToolResponse
import com.brayan.renovar.enum.ToolEmployee
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class ToolsEmployeesModel(
    val employeeId: UUID,
    val toolId: UUID,
    val startDate: LocalDate,
    val endDate: LocalDate? = null,
    val quantity: Int,
    val creationDate: LocalDateTime? = null,
    val updateDate: LocalDateTime? = null,
    val status: ToolEmployee,
    val creationDateId: UUID
){
    fun toResponse(employee:EmployeeResponse, tool:ToolResponse) = EmployeeToolResponse(
        employee = employee,
        tool = tool,
        startDate = startDate,
        endDate = endDate,
        quantity = quantity,
        creationDate = creationDate,
        updateDate = updateDate,
        status = status,
        creationDateId = creationDateId
    )
}
