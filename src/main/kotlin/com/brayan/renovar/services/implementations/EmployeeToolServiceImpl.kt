package com.brayan.renovar.services.implementations

import com.brayan.renovar.api.request.EmployeeToolRequest
import com.brayan.renovar.api.request.EmployeeToolUpdateRequest
import com.brayan.renovar.api.response.EmployeeToolResponse
import com.brayan.renovar.database.entities.ToolsEmployeesKey
import com.brayan.renovar.database.repositories.interfaces.CreationRepository
import com.brayan.renovar.database.repositories.interfaces.EmployeeRepository
import com.brayan.renovar.database.repositories.interfaces.EmployeeToolRepository
import com.brayan.renovar.database.repositories.interfaces.ToolRepository
import com.brayan.renovar.enum.EmployeeStatus
import com.brayan.renovar.enum.ToolEmployee
import com.brayan.renovar.enum.ToolStatus
import com.brayan.renovar.models.ToolsEmployeesModel
import com.brayan.renovar.services.interfaces.EmployeeToolService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class EmployeeToolServiceImpl(
    val employeeToolRepository: EmployeeToolRepository,
    val creationRepository: CreationRepository,
    val employeeRepository: EmployeeRepository,
    val toolRepository: ToolRepository
):EmployeeToolService {
    @Transactional

    override fun save(employeeToolRequest: EmployeeToolRequest): EmployeeToolResponse {
        if (employeeToolRepository.findByToolIdAndEndDateIsNull(employeeToolRequest.toolId).isNotEmpty()) {
            throw Exception("Ferramenta já está emprestada")
        }
        val employee = employeeRepository.findEmployeeById(employeeToolRequest.employeeId)
        if (employee.status != EmployeeStatus.ATIVO){
            throw Exception("Funcionário não está trabalhando")
        }
        val tool = toolRepository.findById(employeeToolRequest.toolId)
        if (tool.toolStatus != ToolStatus.PARADA){
            throw Exception("Ferramenta não está disponível")
        }
        val employeeTool = ToolsEmployeesModel(
            employeeId = employeeToolRequest.employeeId,
            toolId = employeeToolRequest.toolId,
            startDate = employeeToolRequest.startDate,
            quantity = employeeToolRequest.quantity,
            status = employeeToolRequest.status,
            creationDateId = creationRepository.saveCreation() ?: throw Exception("Erro ao salvar data de criação")
        )
        val response = employeeToolRepository.save(employeeTool).toResponse()
        toolRepository.save(tool.copy(toolStatus = ToolStatus.TRABALHANDO))
        return response

    }
    @Transactional

    override fun update(employeeToolUpdateRequest: EmployeeToolUpdateRequest): EmployeeToolResponse {
        val tool = toolRepository.findById(employeeToolUpdateRequest.id.toolId!!)
        if (tool.toolStatus == ToolStatus.PARADA){
            throw Exception("Ferramenta não está emprestada")
        }
        toolRepository.save(tool.copy(toolStatus = ToolStatus.PARADA))
        val employeeTool = employeeToolRepository.findById(employeeToolUpdateRequest.id)
        val employeeToolUpdate = employeeTool.copy(
            endDate = employeeToolUpdateRequest.endDate,
            status = employeeToolUpdateRequest.status
        ).toToolsEmployeesModel()
        return employeeToolRepository.save(employeeToolUpdate).toResponse()
    }

    override fun listAll(): List<EmployeeToolResponse> {
        return employeeToolRepository.findAll().map { it.toResponse() }
    }

    override fun listByEmployeeId(employeeId: UUID): List<EmployeeToolResponse> {
        return employeeToolRepository.findByEmployeeId(employeeId).map { it.toResponse() }
    }

    override fun listByEmployeeIdAndStatusLoaned(employeeId: UUID): List<EmployeeToolResponse> {
        return employeeToolRepository.findByEmployeeIdAndStatus(employeeId, ToolEmployee.ENTREGUE).map { it.toResponse() }
    }

    override fun listByEmployeeIdAndStatusReturned(employeeId: UUID): List<EmployeeToolResponse> {
        return employeeToolRepository.findByEmployeeIdAndStatus(employeeId, ToolEmployee.DEVOLVIDO).map { it.toResponse() }
    }

}