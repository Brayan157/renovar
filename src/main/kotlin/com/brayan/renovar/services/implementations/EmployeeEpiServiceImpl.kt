package com.brayan.renovar.services.implementations

import com.brayan.renovar.api.request.AddEpiToEmployeeRequest
import com.brayan.renovar.api.request.EmployeeEpiIdRequest
import com.brayan.renovar.api.request.ReturnAllEpi
import com.brayan.renovar.api.request.ReturnEpiToEmployeeRequest
import com.brayan.renovar.api.response.EmployeeEpiResponse
import com.brayan.renovar.api.response.EmployeeResponse
import com.brayan.renovar.database.entities.EmployeeEPI
import com.brayan.renovar.database.entities.EmployeeEPIId
import com.brayan.renovar.database.repositories.interfaces.CreationRepository
import com.brayan.renovar.database.repositories.interfaces.EPIRepository
import com.brayan.renovar.database.repositories.interfaces.EmployeeEPIRepository
import com.brayan.renovar.database.repositories.interfaces.EmployeeRepository
import com.brayan.renovar.enum.EPIStatus
import com.brayan.renovar.enum.EmployeeStatus
import com.brayan.renovar.models.EmployeeEPIModel
import com.brayan.renovar.services.interfaces.EmployeeEpiService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class EmployeeEpiServiceImpl (
    val employeeEPIRepository: EmployeeEPIRepository,
    val creationRepository: CreationRepository,
    val employeeRepository: EmployeeRepository,
    val epiRepository: EPIRepository
): EmployeeEpiService{
    @Transactional

    override fun addEpiToEmployee(addEpiToEmployeeRequest: AddEpiToEmployeeRequest): EmployeeEpiResponse {
        // verificar se existe um funcionario com o epi e a data de devolução nula e retornar um bolean na função
        if (employeeEPIRepository.existByEmployeeAndEpiAndReturnDateIsNull(addEpiToEmployeeRequest.employeeId, addEpiToEmployeeRequest.epiId).isNotEmpty()) {
            throw Exception("Funcionário já possui esse EPI")
        }
        val employee = employeeRepository.findEmployeeById(addEpiToEmployeeRequest.employeeId)
        if (employee.status != EmployeeStatus.ATIVO){
            throw Exception("Funcionário não está trabalhando")
        }
        val employeeEpiModel = EmployeeEPIModel(
            employeeId = addEpiToEmployeeRequest.employeeId,
            epiId = addEpiToEmployeeRequest.epiId,
            quantity = addEpiToEmployeeRequest.quantity,
            deliveryDate = addEpiToEmployeeRequest.deliveryDate,
            epiStatus = EPIStatus.ENTREGUE,
            reason = addEpiToEmployeeRequest.reason,
            creationDateId = creationRepository.saveCreation() ?: throw Exception("Erro ao salvar data de criação")
        )
        return employeeEPIRepository.save(employeeEpiModel)
    }
    @Transactional
    override fun returnEpiToEmployee(request: ReturnEpiToEmployeeRequest): EmployeeEpiResponse {
        val key = EmployeeEPIId(
            employeeId = request.employeeId,
            epiId = request.epiId,
            creationDateId = request.creationDateId
        )

        val employeeEpi = employeeEPIRepository.findById(key)

        // Atualiza o EmployeeEPI com a data de devolução e o status
        val updatedEmployeeEpi = employeeEpi.copy(
            returnDate = request.returnDate,
            epiStatus = EPIStatus.DEVOLVIDO
        )

        // Salva o EmployeeEPI atualizado e retorna a resposta
        return employeeEPIRepository.save(updatedEmployeeEpi)
    }

    override fun getEmployeeEpi(): List<EmployeeEpiResponse> {
        return employeeEPIRepository.findAll().map { it.toEmployeeEPIResponse() }
    }

    override fun getEmployeeEpiId(id: EmployeeEPIId): EmployeeEpiResponse {
        val employeeEpi = employeeEPIRepository.findById(id)
        return EmployeeEpiResponse(
            employee = employeeRepository.findEmployeeById(employeeEpi.employeeId),
            epi = epiRepository.findById(employeeEpi.epiId).toResponse(),
            quantity = employeeEpi.quantity,
            deliveryDate = employeeEpi.deliveryDate,
            returnDate = employeeEpi.returnDate,
            epiStatus = employeeEpi.epiStatus,
            reason = employeeEpi.reason,
            creationDateId = employeeEpi.creationDateId
        )
    }

    override fun getEmployeeEpiDelivered(): List<EmployeeEpiResponse> {
        return employeeEPIRepository.findByStatus(EPIStatus.ENTREGUE)
    }

    override fun getEmployeeEpiReturned(): List<EmployeeEpiResponse> {
        return employeeEPIRepository.findByStatus(EPIStatus.DEVOLVIDO)
    }

    override fun returnAllEpiToEmployee(returnAllEpi: ReturnAllEpi): List<EmployeeEpiResponse> {
        val employee = employeeRepository.findEmployeeById(returnAllEpi.employeeId)

        val employeeEpiList = employeeEPIRepository.findByEmployeeIdAndEPIStatus(returnAllEpi.employeeId).map { it.toEmployeeEPIModel() }
        if (employeeEpiList.isEmpty()) {
            throw Exception("Funcionário não possui EPIs")
        }
        val employeeEpiUpdate = employeeEpiList.map {
            if (it.returnDate == null) {
                it.copy(
                    returnDate = returnAllEpi.returnDate,
                    epiStatus = EPIStatus.DEVOLVIDO
                )
            }
            else {
                it
            }
        }
        return employeeEpiUpdate.map { employeeEPIRepository.save(it) }
    }

    override fun getEmployeeEpiByEmployeeId(employeeId: UUID): List<EmployeeEpiResponse> {
        return employeeEPIRepository.findByEmployeeId(employeeId).map { it.toEmployeeEPIResponse() }
    }

    override fun getEmployeeEpiByEpiId(epiId: UUID): List<EmployeeEpiResponse> {
        return employeeEPIRepository.findByEpiId(epiId).map { it.toEmployeeEPIResponse() }
    }

    override fun getEmployeeEpiDeliveredByEmployeeId(employeeId: UUID): List<EmployeeEpiResponse> {
        return employeeEPIRepository.findByEmployeeIdAndEPIStatus(employeeId).map { it.toEmployeeEPIResponse() }
    }


}