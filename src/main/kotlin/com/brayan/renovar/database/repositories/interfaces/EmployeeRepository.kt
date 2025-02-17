package com.brayan.renovar.database.repositories.interfaces

import com.brayan.renovar.api.response.EmployeeResponse
import com.brayan.renovar.database.entities.EPI
import com.brayan.renovar.database.entities.Employee
import com.brayan.renovar.database.entities.Tool
import com.brayan.renovar.database.entities.Work
import com.brayan.renovar.enum.EmployeeStatus
import com.brayan.renovar.models.EmployeeModel
import com.brayan.renovar.models.FunctionModel
import java.util.UUID

interface EmployeeRepository {
    fun save(functionModel: FunctionModel): FunctionModel
    fun findByName(name: String): List<FunctionModel>
    fun findAll(): List<FunctionModel>
    fun findById(id: UUID): FunctionModel
    fun saveEmployee(employeeModel: EmployeeModel): EmployeeResponse
    fun findAllById(ids: List<UUID>): List<Employee>
    fun findLastRegistration(): Int
    fun findAllEmployees(): List<EmployeeResponse>
    fun findEmployeeById(employeeId: UUID): EmployeeResponse
    fun findAllByStatus(status: EmployeeStatus): List<EmployeeResponse>
    fun findAllByName(name: String): List<EmployeeResponse>
    fun findEmployeeByIdModel(employeeId: UUID): EmployeeModel
    fun findEmployeeByRegistration(registration: Int): EmployeeResponse
    fun findEmployeeByCPF(cpf: String): EmployeeResponse


}