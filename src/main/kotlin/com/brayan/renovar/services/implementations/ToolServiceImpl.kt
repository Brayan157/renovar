package com.brayan.renovar.services.implementations

import com.brayan.renovar.api.request.ToolUpdateRequest
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

    override fun save(toolModel: ToolModel): ToolModel {
        return toolRepository.save(toolModel)
    }

    override fun findAll(): List<ToolModel> {
        return toolRepository.findAll()
    }

    override fun findById(id: UUID): ToolModel {
       return toolRepository.findById(id)
    }

    override fun findByName(name: String): List<ToolModel> {
        return toolRepository.findByName(name)
    }

    override fun findByStatus(status: ToolStatus): List<ToolModel> {
        return toolRepository.findByStatus(status)
    }

    override fun updateStatus(toolUpdateRequest: ToolUpdateRequest): ToolModel {
        val tool = toolRepository.findById(toolUpdateRequest.id)
        val toolModel = tool.copy(
            toolStatus = toolUpdateRequest.toolStatus?: tool.toolStatus
        )
        return toolRepository.save(toolModel)
    }

    override fun deleteById(id: UUID): Boolean {
        val tool = toolRepository.findById(id)
        val toolModel = tool.copy(
            toolStatus = ToolStatus.DELETADA
        )
        return if (toolRepository.save(toolModel) != null) true else false
    }

    override fun findByStatusNot(deletada: ToolStatus): List<ToolModel> {
        return toolRepository.findByStatusNot(deletada)
    }


}