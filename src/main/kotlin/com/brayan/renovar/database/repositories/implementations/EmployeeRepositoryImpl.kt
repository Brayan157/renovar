package com.brayan.renovar.database.repositories.implementations

import com.brayan.renovar.api.response.EmployeeResponse
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
        return functionJpaRepository.findByFunctionContainingIgnoreCase(name).map { it.toFunctionModel() }
    }

    override fun findAll(): List<FunctionModel> {
        return functionJpaRepository.findAll().map { it.toFunctionModel() }
    }

    override fun findById(id: UUID): FunctionModel {
        return functionJpaRepository.findById(id).map { it.toFunctionModel() }.orElseThrow()
    }

    override fun saveEmployee(
        employeeModel: EmployeeModel,
    ): EmployeeResponse {
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
        return employeeJpaRepository.save(employee).toEmployeeResponse()

    }

    override fun findAllById(ids: List<UUID>): List<Employee> {
        return employeeJpaRepository.findAllById(ids)
    }

    override fun findLastRegistration(): Int {
        return employeeJpaRepository.findLastRegistration()
    }

    override fun findAllEmployees(): List<EmployeeResponse> {
        return employeeJpaRepository.findAll().map { it.toEmployeeResponse() }
    }

    override fun findEmployeeById(employeeId: UUID): EmployeeResponse {
        return employeeJpaRepository.findById(employeeId).map { it.toEmployeeResponse()}.orElseThrow()
    }

    override fun findAllByStatus(status: EmployeeStatus): List<EmployeeResponse> {
        return employeeJpaRepository.findByEmployeeStatus(status).map { it.toEmployeeResponse() }
    }

    override fun findAllByName(name: String): List<EmployeeResponse> {
        return employeeJpaRepository.findByNameContainingIgnoreCase(name).map { it.toEmployeeResponse() }
    }

    override fun findEmployeeByIdModel(employeeId: UUID): EmployeeModel {
        return employeeJpaRepository.findById(employeeId).map { it.toEmployeeModel() }.orElseThrow()
    }

    override fun findEmployeeByRegistration(registration: Int): EmployeeResponse {
        return employeeJpaRepository.findByRegistration(registration).toEmployeeResponse()
    }

    override fun findEmployeeByCPF(cpf: String): EmployeeResponse {
        return employeeJpaRepository.findByCpf(cpf).toEmployeeResponse()
    }

}