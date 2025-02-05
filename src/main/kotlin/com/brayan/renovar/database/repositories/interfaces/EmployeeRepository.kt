package com.brayan.renovar.database.repositories.interfaces

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
    fun saveEmployee(employeeModel: EmployeeModel): EmployeeModel
    fun findAllById(ids: List<UUID>): List<Employee>
    fun findLastRegistration(): String
    fun findAllEmployees(): List<EmployeeModel>
    fun findEmployeeById(employeeId: UUID): EmployeeModel
    fun findAllByStatus(status: EmployeeStatus): List<EmployeeModel>
    fun findAllByName(name: String): List<EmployeeModel>


}