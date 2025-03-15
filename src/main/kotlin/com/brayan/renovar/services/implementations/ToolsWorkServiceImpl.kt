package com.brayan.renovar.services.implementations

import com.brayan.renovar.api.request.ToolsWorkResquest
import com.brayan.renovar.api.request.ToolsWorkUpdateResquest
import com.brayan.renovar.api.response.ToolsWorkResponse
import com.brayan.renovar.database.entities.ToolsWorkId
import com.brayan.renovar.database.repositories.interfaces.CreationRepository
import com.brayan.renovar.database.repositories.interfaces.ToolRepository
import com.brayan.renovar.database.repositories.interfaces.ToolsWorkRepository
import com.brayan.renovar.database.repositories.interfaces.WorkRepository
import com.brayan.renovar.models.ToolsWorkModel
import com.brayan.renovar.services.interfaces.ToolsWorkService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ToolsWorkServiceImpl(
    val toolsWorkRepository: ToolsWorkRepository,
    val creationRepository: CreationRepository,
    val toolRepository: ToolRepository,
    val workRepository: WorkRepository
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
        return toolsWorkRepository.save(toolsWork).toResponse(
            tool = toolRepository.findById(toolsWorkRequest.toolsId).toResponse(),
            work = workRepository.findById(toolsWorkRequest.workId).toResponse()
        )
    }

    override fun update(toolsWorkRequest: ToolsWorkUpdateResquest): ToolsWorkResponse {
        val toolsWork = toolsWorkRepository.findById(ToolsWorkId(
            toolsId = toolsWorkRequest.toolsId,
            workId = toolsWorkRequest.workId,
            creationDateId = toolsWorkRequest.creationDateId
        ))
        val toolsWorkUpdate = toolsWork.copy(
            exitDate = toolsWorkRequest.exitDate,
        )
        return toolsWorkRepository.save(toolsWorkUpdate).toResponse(
            tool = toolRepository.findById(toolsWorkRequest.toolsId).toResponse(),
            work = workRepository.findById(toolsWorkRequest.workId).toResponse()
        )
    }

    override fun listAll(): List<ToolsWorkResponse> {
        return toolsWorkRepository.findAll().map { it.toResponse(
            tool = toolRepository.findById(it.toolsId).toResponse(),
            work = workRepository.findById(it.workId).toResponse()
        ) }
    }

    override fun listByWorkId(workId: UUID): List<ToolsWorkResponse> {
        return toolsWorkRepository.findByWorkId(workId).map { it.toResponse(
            tool = toolRepository.findById(it.toolsId).toResponse(),
            work = workRepository.findById(it.workId).toResponse()
        ) }
    }

    override fun listByWorkIdAndStatusLoaned(workId: UUID): List<ToolsWorkResponse> {
        return toolsWorkRepository.findByWorkIdAndExitDateIsNull(workId).map {
            it.toResponse(
                tool = toolRepository.findById(it.toolsId).toResponse(),
                work = workRepository.findById(it.workId).toResponse()
            )
        }
    }

    override fun listByWorkIdAndStatusReturned(workId: UUID): List<ToolsWorkResponse> {
        return toolsWorkRepository.findByWorkIdAndExitDateIsNotNull(workId).map {
            it.toResponse(
                tool = toolRepository.findById(it.toolsId).toResponse(),
                work = workRepository.findById(it.workId).toResponse()
            )
        }
    }

    override fun listByWorkIdAndToolId(toolsWorkId: ToolsWorkId): ToolsWorkResponse {
        return toolsWorkRepository.findById(toolsWorkId).toResponse(
            tool = toolRepository.findById(toolsWorkId.toolsId!!).toResponse(),
            work = workRepository.findById(toolsWorkId.workId!!).toResponse()
        )
    }

}
