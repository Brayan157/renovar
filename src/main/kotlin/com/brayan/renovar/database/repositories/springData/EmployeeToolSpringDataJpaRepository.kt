package com.brayan.renovar.database.repositories.springData

import com.brayan.renovar.database.entities.Employee
import com.brayan.renovar.database.entities.ToolsEmployees
import com.brayan.renovar.database.entities.ToolsEmployeesKey
import com.brayan.renovar.enum.ToolEmployee
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface EmployeeToolSpringDataJpaRepository:JpaRepository<ToolsEmployees, ToolsEmployeesKey> {
    fun findByToolIdAndEndDateIsNull(toolId: UUID): List<ToolsEmployees>
    fun findByEmployee(get: Employee): List<ToolsEmployees>
    fun findByEmployeeAndStatus(get: Employee, entregue: ToolEmployee): List<ToolsEmployees>

}
