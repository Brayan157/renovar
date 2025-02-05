package com.brayan.renovar.services.interfaces

import com.brayan.renovar.api.request.ADDEPISEmployee
import com.brayan.renovar.api.request.AddToolsForEmployeeRequest
import com.brayan.renovar.api.request.EmployeeUpdateRequest
import com.brayan.renovar.api.request.FunctionRequest
import com.brayan.renovar.api.request.RemoveToolEmployeeRequest
import com.brayan.renovar.api.request.ReturnEpi
import com.brayan.renovar.enum.EmployeeStatus
import com.brayan.renovar.models.EmployeeModel
import com.brayan.renovar.models.FunctionModel
import java.util.UUID

interface EmployeeService {
    fun createFunction(functionRequest: FunctionRequest): FunctionModel
    fun getFunctionById(id: UUID): FunctionModel
    fun getFunctionByName(name: String): List<FunctionModel>
    fun getFunctions(): List<FunctionModel>
    fun createEmployee(employeeModel: EmployeeModel): EmployeeModel
//    fun addEmployeeToWork(employeeId: UUID, workId: UUID)
    fun generateRegistration(): String
    fun getEmployees(): List<EmployeeModel>
    fun getEmployeeById(employeeId: UUID): EmployeeModel
    fun updateEmployee(employeeRequest: EmployeeUpdateRequest): EmployeeModel
    fun getEmployeesByStatus(status: EmployeeStatus): List<EmployeeModel>
    fun getEmployeesByName(name: String): List<EmployeeModel>

    fun findAllIds(ids: List<UUID>): List<EmployeeModel>
    fun addEPI(addepisEmployee: ADDEPISEmployee): EmployeeModel
    fun returnEPI(returnEpi: ReturnEpi): EmployeeModel
    fun addToolsForEmployee(addToolsForEmployeeRequest: AddToolsForEmployeeRequest): EmployeeModel
    fun removeToolsForEmployee(removeToolsFroEmployee: RemoveToolEmployeeRequest): EmployeeModel

}