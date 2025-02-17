package com.brayan.renovar.services.interfaces

import com.brayan.renovar.api.request.ADDEPISEmployee
import com.brayan.renovar.api.request.AddToolsForEmployeeRequest
import com.brayan.renovar.api.request.EmployeeUpdateRequest
import com.brayan.renovar.api.request.FunctionRequest
import com.brayan.renovar.api.request.RemoveToolEmployeeRequest
import com.brayan.renovar.api.request.ReturnEpi
import com.brayan.renovar.api.response.EmployeeResponse
import com.brayan.renovar.enum.EmployeeStatus
import com.brayan.renovar.models.EmployeeModel
import com.brayan.renovar.models.FunctionModel
import java.util.UUID

interface EmployeeService {
    fun createFunction(functionRequest: FunctionRequest): FunctionModel
    fun getFunctionById(id: UUID): FunctionModel
    fun getFunctionByName(name: String): List<FunctionModel>
    fun getFunctions(): List<FunctionModel>
    fun createEmployee(employeeModel: EmployeeModel): EmployeeResponse
//    fun addEPIToEmployee(addepisEmployee: ADDEPISEmployee): EmployeeModel
    fun generateRegistration(): Int
    fun getEmployees(): List<EmployeeResponse>
    fun getEmployeeById(employeeId: UUID): EmployeeResponse
    fun updateEmployee(employeeRequest: EmployeeUpdateRequest): EmployeeResponse
    fun getEmployeesByStatus(status: EmployeeStatus): List<EmployeeResponse>
    fun getEmployeesByName(name: String): List<EmployeeResponse>

    fun findAllIds(ids: List<UUID>): List<EmployeeResponse>
    fun returnEPI(returnEpi: ReturnEpi): EmployeeModel
    fun addToolsForEmployee(addToolsForEmployeeRequest: AddToolsForEmployeeRequest): EmployeeModel
    fun removeToolsForEmployee(removeToolsFroEmployee: RemoveToolEmployeeRequest): EmployeeModel
    fun addEPIToEmployee(addepisEmployee: ADDEPISEmployee): EmployeeModel
    fun getEmployeeByRegistration(registration: Int): EmployeeResponse
    fun getEmployeeByCPF(cpf: String): EmployeeResponse
    fun getEmployeeModel(id: UUID): EmployeeModel

}