package com.brayan.renovar.services.implementations

import com.brayan.renovar.api.request.AddEpiToEmployeeRequest
import com.brayan.renovar.api.request.ReturnEpiToEmployeeRequest
import com.brayan.renovar.api.response.EmployeeEpiResponse
import com.brayan.renovar.database.entities.EmployeeEPIId
import com.brayan.renovar.database.repositories.interfaces.CreationRepository
import com.brayan.renovar.database.repositories.interfaces.EmployeeEPIRepository
import com.brayan.renovar.enum.EPIStatus
import com.brayan.renovar.models.EmployeeEPIModel
import com.brayan.renovar.services.interfaces.EmployeeEpiService
import org.springframework.stereotype.Service

@Service
class EmployeeEpiServiceImpl (
    val employeeEPIRepository: EmployeeEPIRepository,
    val creationRepository: CreationRepository
): EmployeeEpiService{
    override fun addEpiToEmployee(addEpiToEmployeeRequest: AddEpiToEmployeeRequest): EmployeeEpiResponse {
        val employeeEpiModel = EmployeeEPIModel(
            employeeId = addEpiToEmployeeRequest.employeeId,
            epiId = addEpiToEmployeeRequest.epiId,
            quantity = addEpiToEmployeeRequest.quantity,
            deliveryDate = addEpiToEmployeeRequest.deliveryDate,
            epiStatus = EPIStatus.ENTREGUE,
            reason = addEpiToEmployeeRequest.reason,
            creationDateId = creationRepository.saveCreation() ?: throw Exception("Erro ao salvar data de criação"),
        )
        return employeeEPIRepository.save(employeeEpiModel).toEmployeeEPIResponse()
    }

    override fun ReturnEpiToEmployee(returnEpiToEmployeeRequest: ReturnEpiToEmployeeRequest): EmployeeEpiResponse {
        // verificar se returnEpiToEmployeeRequest.employee.employeeEpis está vazio
        if (returnEpiToEmployeeRequest.employee.employeeEpis.isEmpty()) {
            throw Exception("O funcionário não possui EPIs")
        }
        val employeeEpiModel = EmployeeEPIModel(
            employeeId = returnEpiToEmployeeRequest.employee.id!!,
            epiId = returnEpiToEmployeeRequest.epiId,
            quantity = returnEpiToEmployeeRequest.quantity,
            deliveryDate = returnEpiToEmployeeRequest.,
            returnDate = returnEpiToEmployeeRequest.returnDate,
            epiStatus = EPIStatus.DEVOLVIDO,
            reason = returnEpiToEmployeeRequest,
            creationDateId = creationRepository.saveCreation() ?: throw Exception("Erro ao salvar data de criação"),
        )
        return employeeEPIRepository.save(employeeEpiModel).toEmployeeEPIResponse()
    }

    override fun getEmployeeEpi(): EmployeeEPIModel {
        val id = EmployeeEPIId(
            employeeId = employeeId,
            epiId = epiId
        )
        return employeeEPIRepository.findById(id: EmployeeEPIId)
    }

}