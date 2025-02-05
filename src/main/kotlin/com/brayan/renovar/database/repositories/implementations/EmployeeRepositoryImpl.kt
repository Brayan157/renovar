package com.brayan.renovar.database.repositories.implementations

import com.brayan.renovar.database.entities.Employee
import com.brayan.renovar.database.entities.Function
import com.brayan.renovar.database.repositories.interfaces.EmployeeRepository
import com.brayan.renovar.database.repositories.springData.EPISpringDataRepository
import com.brayan.renovar.database.repositories.springData.EmployeeSpringDataRepository
import com.brayan.renovar.database.repositories.springData.FunctionSpringDataRepository
import com.brayan.renovar.database.repositories.springData.ToolSpringDataRepository
import com.brayan.renovar.database.repositories.springData.WorkSpringDataRepository
import com.brayan.renovar.enum.EmployeeStatus
import com.brayan.renovar.models.EmployeeModel
import com.brayan.renovar.models.FunctionModel
import org.springframework.stereotype.Component
import java.util.UUID
@Component
class EmployeeRepositoryImpl(
    val functionJpaRepository: FunctionSpringDataRepository,
    val employeeJpaRepository: EmployeeSpringDataRepository,
    val workRepository: WorkSpringDataRepository,
    val toolRepository: ToolSpringDataRepository,
    val epiRepository: EPISpringDataRepository


):EmployeeRepository {
    override fun save(functionModel: FunctionModel): FunctionModel {
        val function = Function.of(functionModel)
        return functionJpaRepository.save(function).toFunctionModel()
    }

    override fun findByName(name: String): List<FunctionModel> {
        return functionJpaRepository.findByFunction(name).map { it.toFunctionModel() }
    }

    override fun findAll(): List<FunctionModel> {
        return functionJpaRepository.findAll().map { it.toFunctionModel() }
    }

    override fun findById(id: UUID): FunctionModel {
        return functionJpaRepository.findById(id).map { it.toFunctionModel() }.orElseThrow()
    }

    override fun saveEmployee(
        employeeModel: EmployeeModel,
    ): EmployeeModel {
        val works = if (employeeModel.employeesWorks.isNotEmpty()) {
            workRepository.findAllById(employeeModel.employeesWorks.map { it.workId })
        } else emptyList()
        val tools = if (employeeModel.toolsEmployee.isNotEmpty()) {
            toolRepository.findAllById(employeeModel.toolsEmployee.map { it.toolId })
        } else emptyList()
        val epis = if (employeeModel.employeeEpis.isNotEmpty()) {
            epiRepository.findAllById(employeeModel.employeeEpis.map { it.epiId })
        } else emptyList()
        val employee = Employee.of(employeeModel, works, epis, tools)
        return employeeJpaRepository.save(employee).toEmployeeModel()
    }

    override fun findAllById(ids: List<UUID>): List<Employee> {
        return employeeJpaRepository.findAllById(ids)
    }

    override fun findLastRegistration(): String {
        return employeeJpaRepository.findLastRegistration().toString()
    }

    override fun findAllEmployees(): List<EmployeeModel> {
        return employeeJpaRepository.findAll().map { it.toEmployeeModel() }
    }

    override fun findEmployeeById(employeeId: UUID): EmployeeModel {
        return employeeJpaRepository.findById(employeeId).map { it.toEmployeeModel() }.orElseThrow()
    }

    override fun findAllByStatus(status: EmployeeStatus): List<EmployeeModel> {
        return employeeJpaRepository.findByEmployeeStatus(status).map { it.toEmployeeModel() }
    }

    override fun findAllByName(name: String): List<EmployeeModel> {
        return employeeJpaRepository.findByNameContainingIgnoreCase(name).map { it.toEmployeeModel() }
    }

}