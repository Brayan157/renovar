package com.brayan.renovar.services.implementations

import com.brayan.renovar.api.request.EmployeeUpdateRequest
import com.brayan.renovar.api.request.FunctionRequest
import com.brayan.renovar.database.repositories.interfaces.EmployeeRepository
import com.brayan.renovar.enum.EmployeeStatus
import com.brayan.renovar.models.EmployeeModel
import com.brayan.renovar.models.FunctionModel
import com.brayan.renovar.services.interfaces.EmployeeService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class EmployeeServiceImpl(
    val EmployeeRepository: EmployeeRepository
):EmployeeService{
    override fun createFunction(functionRequest: FunctionRequest): FunctionModel {
        val functionModel = FunctionModel(
            function = functionRequest.name,
            description = functionRequest.description
        )
        return EmployeeRepository.save(functionModel)
    }

    override fun getFunctionById(id: UUID): FunctionModel {
        return EmployeeRepository.findById(id)
    }

    override fun getFunctionByName(name: String): List<FunctionModel> {
        return EmployeeRepository.findByName(name)
    }

    override fun getFunctions(): List<FunctionModel> {
        return EmployeeRepository.findAll()
    }

    override fun createEmployee(employeeModel: EmployeeModel): EmployeeModel {
        val employee = employeeModel.copy(
            registration = generateRegistration()
        )
        return EmployeeRepository.saveEmployee(employee)
    }

    override fun generateRegistration(): String {
        return EmployeeRepository.findLastRegistration()
    }

    override fun getEmployees(): List<EmployeeModel> {
        return EmployeeRepository.findAllEmployees()
    }

    override fun getEmployeeById(employeeId: UUID): EmployeeModel {
        return EmployeeRepository.findEmployeeById(employeeId)
    }

    override fun    updateEmployee(employeeRequest: EmployeeUpdateRequest): EmployeeModel {
        val employeeModel = getEmployeeById(employeeRequest.id)
        val employee = employeeModel.copy(
            name = employeeRequest.name?: employeeModel.name,
            functionModel = employeeRequest.functionModel?: employeeModel.functionModel,
            phones = employeeRequest.phones?: employeeModel.phones,
            hourlyRate = employeeRequest.hourlyRate?: employeeModel.hourlyRate,
            employeeStatus = employeeRequest.status?: employeeModel.employeeStatus
        )
        return EmployeeRepository.saveEmployee(employee)
    }

    override fun getEmployeesByStatus(status: EmployeeStatus): List<EmployeeModel> {
        return EmployeeRepository.findAllByStatus(status)
    }


}