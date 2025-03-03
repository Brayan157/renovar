package com.brayan.renovar.services.implementations

import com.brayan.renovar.api.request.ToolUpdateRequest
import com.brayan.renovar.api.request.ToolUpdateStatusRequest
import com.brayan.renovar.api.response.ToolResponse
import com.brayan.renovar.database.repositories.interfaces.ToolRepository
import com.brayan.renovar.enum.ToolStatus
import com.brayan.renovar.models.ToolModel
import com.brayan.renovar.services.interfaces.ToolsService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ToolServiceImpl(
    val toolRepository: ToolRepository
): ToolsService {

    override fun save(toolModel: ToolModel): ToolResponse{
        return toolRepository.save(toolModel).toResponse()
    }

    override fun findAll(): List<ToolResponse> {
        return toolRepository.findAll().map { it.toResponse() }
    }

    override fun findById(id: UUID): ToolResponse {
       return toolRepository.findById(id).toResponse()
    }

    override fun findByName(name: String): List<ToolResponse> {
        return toolRepository.findByName(name).map { it.toResponse() }
    }

    override fun findByStatus(status: ToolStatus): List<ToolResponse> {
        return toolRepository.findByStatus(status).map { it.toResponse() }
    }


    override fun deleteById(id: UUID): Boolean {
        val tool = toolRepository.findById(id)
        val toolModel = tool.copy(
            toolStatus = ToolStatus.DELETADA
        )
        return if (toolRepository.save(toolModel) != null) true else false
    }

    override fun findByStatusNot(deletada: ToolStatus): List<ToolResponse> {
        return toolRepository.findByStatusNot(deletada).map { it.toResponse() }
    }

    override fun update(toolUpdade: ToolUpdateRequest): ToolResponse {
        val toolModel = toolRepository.findById(toolUpdade.id)
        val toolUpdate = toolModel.copy(
              name = toolUpdade.name ?: toolModel.name,
              purchaseDate = toolUpdade.purchaseDate ?: toolModel.purchaseDate,
              unitValue = toolUpdade.unitValue ?: toolModel.unitValue,
              toolStatus = toolUpdade.toolStatus ?: toolModel.toolStatus,
              quantity = toolUpdade.quantity ?: toolModel.quantity
        )
        return toolRepository.save(toolUpdate).toResponse()
    }


}