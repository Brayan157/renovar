package com.brayan.renovar.services.implementations

import com.brayan.renovar.api.request.EmployeeUpdateRequest
import com.brayan.renovar.api.request.FunctionRequest
import com.brayan.renovar.api.response.EmployeeResponse
import com.brayan.renovar.database.repositories.interfaces.EmployeeRepository
import com.brayan.renovar.enum.EmployeeStatus
import com.brayan.renovar.models.EmployeeModel
import com.brayan.renovar.models.FunctionModel
import com.brayan.renovar.services.interfaces.EmployeeService
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class EmployeeServiceImpl(
    val employeeRepository: EmployeeRepository,
    private val entityManager: EntityManager,
):EmployeeService{
    override fun createFunction(functionRequest: FunctionRequest): FunctionModel {
        val functionModel = FunctionModel(
            function = functionRequest.name,
            description = functionRequest.description
        )
        return employeeRepository.save(functionModel)
    }

    override fun getFunctionById(id: UUID): FunctionModel {
        return employeeRepository.findById(id)
    }

    override fun getFunctionByName(name: String): List<FunctionModel> {
        return employeeRepository.findByName(name)
    }

    override fun getFunctions(): List<FunctionModel> {
        return employeeRepository.findAll()
    }

    override fun createEmployee(employeeModel: EmployeeModel): EmployeeResponse {
        val query = entityManager.createNativeQuery("SELECT nextval('registration_sequence')")
        val nextRegistration = (query.singleResult as Number).toInt()
        val employee = employeeModel.copy(
            registration = nextRegistration,
            employeeStatus = EmployeeStatus.ATIVO
        )
        return employeeRepository.saveEmployee(employee)
    }

    override fun generateRegistration(): Int {
        return employeeRepository.findLastRegistration() + 1
    }

    override fun getEmployees(): List<EmployeeResponse> {
        return employeeRepository.findAllEmployees()
    }

    override fun getEmployeeById(employeeId: UUID): EmployeeResponse {
        return employeeRepository.findEmployeeById(employeeId)
    }


    override fun updateEmployee(employeeRequest: EmployeeUpdateRequest): EmployeeResponse {
        val employeeModel = employeeRepository.findEmployeeByIdModel(employeeRequest.id)
        val employee = employeeModel.copy(
            name = employeeRequest.name?: employeeModel.name,
            functionModel = employeeRequest.functionModel?: employeeModel.functionModel,
            phones = employeeRequest.phones?: employeeModel.phones,
            hourlyRate = employeeRequest.hourlyRate?: employeeModel.hourlyRate,
            employeeStatus = employeeRequest.status?: employeeModel.employeeStatus
        )
        return employeeRepository.saveEmployee(employee)
    }

    override fun getEmployeesByStatus(status: EmployeeStatus): List<EmployeeResponse> {
        return employeeRepository.findAllByStatus(status)
    }

    override fun getEmployeesByName(name: String): List<EmployeeResponse> {
        return employeeRepository.findAllByName(name)
    }

    override fun findAllIds(ids:List<UUID>): List<EmployeeResponse> {
        return employeeRepository.findAllById(ids).map { it.toEmployeeResponse() }
    }

    override fun getEmployeeByRegistration(registration: Int): EmployeeResponse {
        return employeeRepository.findEmployeeByRegistration(registration)
    }

    override fun getEmployeeByCPF(cpf: String): EmployeeResponse {
        return employeeRepository.findEmployeeByCPF(cpf)
    }

    override fun getEmployeeModel(id: UUID): EmployeeModel {
        return employeeRepository.findEmployeeByIdModel(id)
    }

}