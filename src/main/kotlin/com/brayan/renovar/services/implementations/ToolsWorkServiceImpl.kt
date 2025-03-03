package com.brayan.renovar.services.implementations

import com.brayan.renovar.api.request.ToolsWorkResquest
import com.brayan.renovar.api.request.ToolsWorkUpdateResquest
import com.brayan.renovar.api.response.ToolsWorkResponse
import com.brayan.renovar.database.entities.ToolsWorkId
import com.brayan.renovar.database.repositories.interfaces.CreationRepository
import com.brayan.renovar.database.repositories.interfaces.ToolsWorkRepository
import com.brayan.renovar.models.ToolsWorkModel
import com.brayan.renovar.services.interfaces.ToolsWorkService
import org.springframework.stereotype.Service

@Service
class ToolsWorkServiceImpl(
    val toolsWorkRepository: ToolsWorkRepository,
    val creationRepository: CreationRepository
):ToolsWorkService {
    override fun save(toolsWorkRequest: ToolsWorkResquest): ToolsWorkResponse {
        if (toolsWorkRepository.findByToolIdAndExitDateIsNull(toolsWorkRequest.toolsId).isNotEmpty()) {
            throw Exception("Ferramenta já está sendo utilizada em outra obra")
        }
        val toolsWork = ToolsWorkModel(
            toolsId = toolsWorkRequest.toolsId,
            workId = toolsWorkRequest.workId,
            reason = toolsWorkRequest.reason,
            entryDate = toolsWorkRequest.entryDate,
            creationDateId = creationRepository.saveCreation() ?: throw Exception("Erro ao salvar data de criação")
        )
        return toolsWorkRepository.save(toolsWork).toResponse()
    }

    override fun update(toolsWorkRequest: ToolsWorkUpdateResquest): ToolsWorkResponse {
        val toolsWork = toolsWorkRepository.findById(ToolsWorkId(
            toolsId = toolsWorkRequest.toolsId,
            workId = toolsWorkRequest.workId,
            creationDateId = toolsWorkRequest.creationDateId
        ))
        val toolsWorkUpdate = toolsWork.copy(
            exitDate = toolsWorkRequest.exitDate,
        ).toToolsWorkModel()
        return toolsWorkRepository.save(toolsWorkUpdate).toResponse()
    }
}
