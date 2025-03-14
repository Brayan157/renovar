package com.brayan.renovar.database.repositories.implementations

import com.brayan.renovar.database.entities.ToolsEmployees
import com.brayan.renovar.database.entities.ToolsEmployeesKey
import com.brayan.renovar.database.repositories.interfaces.EmployeeToolRepository
import com.brayan.renovar.database.repositories.springData.CreationSpringDataRepository
import com.brayan.renovar.database.repositories.springData.EmployeeSpringDataRepository
import com.brayan.renovar.database.repositories.springData.EmployeeToolSpringDataJpaRepository
import com.brayan.renovar.database.repositories.springData.ToolSpringDataRepository
import com.brayan.renovar.enum.ToolEmployee
import com.brayan.renovar.enum.ToolStatus
import com.brayan.renovar.models.ToolsEmployeesModel
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class EmployeeToolRepositoryImpl(
    val employeeToolJpaRepository: EmployeeToolSpringDataJpaRepository,
    val creationDataRepository: CreationSpringDataRepository,
    val employeeRepository: EmployeeSpringDataRepository,
    val toolRepository: ToolSpringDataRepository
): EmployeeToolRepository {
    override fun findByToolIdAndEndDateIsNull(toolId: UUID): List<ToolsEmployeesModel> {
        return employeeToolJpaRepository.findByToolIdAndEndDateIsNull(toolId).map { it.toToolsEmployeesModel()}
    }

    override fun save(employeeTool: ToolsEmployeesModel): ToolsEmployeesModel {
        val toolsEmployees = ToolsEmployees(
            id = ToolsEmployeesKey(
                employeeId = employeeTool.employeeId,
                toolId = employeeTool.toolId,
                creationDateId = employeeTool.creationDateId
            ),
            tool = toolRepository.findById(employeeTool.toolId).get(),
            employee = employeeRepository.findById(employeeTool.employeeId).get(),
            startDate = employeeTool.startDate,
            endDate = employeeTool.endDate,
            quantity = employeeTool.quantity,
            status = employeeTool.status,
            creationDateEntity = creationDataRepository.findById(employeeTool.creationDateId).get()
        )
        return employeeToolJpaRepository.save(toolsEmployees).toToolsEmployeesModel()
    }

    override fun findById(id: ToolsEmployeesKey): ToolsEmployeesModel {
        return employeeToolJpaRepository.findById(id).get().toToolsEmployeesModel()
    }

    override fun findAll(): List<ToolsEmployeesModel> {
        return employeeToolJpaRepository.findAll().map { it.toToolsEmployeesModel() }
    }

    override fun findByEmployeeId(employeeId: UUID): List<ToolsEmployeesModel> {
        val employees = employeeRepository.findById(employeeId)
        return employeeToolJpaRepository.findByEmployee(employees.get()).map { it.toToolsEmployeesModel() }
    }

    override fun findByEmployeeIdAndStatus(employeeId: UUID, status: ToolEmployee): List<ToolsEmployeesModel> {
        val employees = employeeRepository.findById(employeeId)
        return employeeToolJpaRepository.findByEmployeeAndStatus(employees.get(), status).map { it.toToolsEmployeesModel() }
    }

}