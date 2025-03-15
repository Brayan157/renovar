package com.brayan.renovar.database.repositories.implementations

import com.brayan.renovar.database.entities.ToolsWork
import com.brayan.renovar.database.entities.ToolsWorkId
import com.brayan.renovar.database.repositories.interfaces.ToolsWorkRepository
import com.brayan.renovar.database.repositories.springData.CreationSpringDataRepository
import com.brayan.renovar.database.repositories.springData.ToolSpringDataRepository
import com.brayan.renovar.database.repositories.springData.ToolsWorkSpringDataRepository
import com.brayan.renovar.database.repositories.springData.WorkSpringDataRepository
import com.brayan.renovar.models.ToolsWorkModel
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ToolsWorkRepositoryImpl(
    val toolsWorkSpringDataRepository: ToolsWorkSpringDataRepository,
    val toolJpaRepository: ToolSpringDataRepository,
    val workJpaRepository: WorkSpringDataRepository,
    val creationDataJpaRepository: CreationSpringDataRepository
): ToolsWorkRepository {
    override fun findByToolIdAndExitDateIsNull(toolId: UUID): List<ToolsWorkModel> {
        return toolsWorkSpringDataRepository.findByToolIdAndExitDateIsNull(toolId).map { it.toToolsWorkModel() }
    }

    override fun save(toolsWork: ToolsWorkModel): ToolsWorkModel {
        val toolsWorkSave = ToolsWork(
            id = ToolsWorkId(
                toolsId = toolsWork.toolsId,
                workId = toolsWork.workId,
                creationDateId = toolsWork.creationDateId
            ),
            tool = toolJpaRepository.findById(toolsWork.toolsId).orElseThrow { Exception("Ferramenta não encontrada") },
            work = workJpaRepository.findById(toolsWork.workId).orElseThrow { Exception("Obra não encontrada") },
            reason = toolsWork.reason,
            entryDate = toolsWork.entryDate,
            creationDateEntity = creationDataJpaRepository.findById(toolsWork.creationDateId).orElseThrow { Exception("Data de criação não encontrada") }
        )
        return toolsWorkSpringDataRepository.save(toolsWorkSave).toToolsWorkModel()
    }

    override fun findById(id: ToolsWorkId): ToolsWorkModel {
        return toolsWorkSpringDataRepository.findById(id).orElseThrow().toToolsWorkModel()
    }

    override fun findAll(): List<ToolsWorkModel> {
        return toolsWorkSpringDataRepository.findAll().map { it.toToolsWorkModel() }
    }

    override fun findByWorkId(workId: UUID): List<ToolsWorkModel> {
        val work = workJpaRepository.findById(workId).orElseThrow { Exception("Obra não encontrada") }
        return toolsWorkSpringDataRepository.findByWork(work).map { it.toToolsWorkModel() }
    }

    override fun findByWorkIdAndExitDateIsNull(workId: UUID): List<ToolsWorkModel> {
        val work = workJpaRepository.findById(workId).orElseThrow { Exception("Obra não encontrada") }
        return toolsWorkSpringDataRepository.findByWorkAndExitDateIsNull(work).map { it.toToolsWorkModel() }
    }

    override fun findByWorkIdAndExitDateIsNotNull(workId: UUID): List<ToolsWorkModel> {
        val work = workJpaRepository.findById(workId).orElseThrow { Exception("Obra não encontrada") }
        return toolsWorkSpringDataRepository.findByWorkAndExitDateIsNotNull(work).map { it.toToolsWorkModel() }
    }


}