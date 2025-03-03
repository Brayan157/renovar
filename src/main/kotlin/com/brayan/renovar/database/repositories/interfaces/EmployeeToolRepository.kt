package com.brayan.renovar.database.repositories.interfaces

import com.brayan.renovar.database.entities.ToolsEmployees
import com.brayan.renovar.database.entities.ToolsEmployeesKey
import com.brayan.renovar.enum.ToolEmployee
import com.brayan.renovar.enum.ToolStatus
import com.brayan.renovar.models.ToolsEmployeesModel
import java.util.UUID

interface EmployeeToolRepository {
    fun findByToolIdAndEndDateIsNull(toolId: UUID): List<ToolsEmployeesModel>
    fun save(employeeTool: ToolsEmployeesModel): ToolsEmployees
    fun findById(id: ToolsEmployeesKey): ToolsEmployees
    fun findAll(): List<ToolsEmployees>
    fun findByEmployeeId(employeeId: UUID): List<ToolsEmployees>
    fun findByEmployeeIdAndStatus(employeeId: UUID, status: ToolEmployee): List<ToolsEmployees>

}
