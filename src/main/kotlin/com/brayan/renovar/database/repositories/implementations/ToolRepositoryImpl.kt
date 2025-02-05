package com.brayan.renovar.database.repositories.implementations

import com.brayan.renovar.database.entities.Tool
import com.brayan.renovar.database.repositories.interfaces.ToolRepository
import com.brayan.renovar.database.repositories.springData.EmployeeSpringDataRepository
import com.brayan.renovar.database.repositories.springData.ToolSpringDataRepository
import com.brayan.renovar.database.repositories.springData.WorkSpringDataRepository
import com.brayan.renovar.enum.ToolStatus
import com.brayan.renovar.models.ToolModel
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ToolRepositoryImpl(
    val toolJpaRepository: ToolSpringDataRepository,
    val employeeRepository: EmployeeSpringDataRepository,
    val workRepository: WorkSpringDataRepository
):ToolRepository {
    override fun findAllById(ids: List<UUID>): List<Tool> {
        return toolJpaRepository.findAllById(ids)
    }
    override fun save(toolModel: ToolModel): ToolModel {
        val employees = if (toolModel.toolsEmployees.isNotEmpty()) {
            employeeRepository.findAllById(toolModel.toolsEmployees.map { it.employeeId })
        } else emptyList()
        val work = if (toolModel.toolsWorks.isEmpty()) {
            workRepository.findAllById(toolModel.toolsWorks.map { it.workId })
        } else emptyList()
        val tool = Tool.of(toolModel, work, employees)
        return toolJpaRepository.save(tool).toToolModel()
    }

    override fun findAll(): List<ToolModel> {
        return toolJpaRepository.findAll().map { it.toToolModel() }
    }

    override fun findById(id: UUID): ToolModel {
        return toolJpaRepository.findById(id).get().toToolModel()
    }

    override fun findByName(name: String): List<ToolModel> {
        return toolJpaRepository.findByNameContainingIgnoreCase(name).map { it.toToolModel() }
    }

    override fun findByStatus(status: ToolStatus): List<ToolModel> {
        return toolJpaRepository.findByToolStatus(status).map { it.toToolModel() }
    }

    override fun findByStatusNot(deletada: ToolStatus): List<ToolModel> {
        return toolJpaRepository.findByToolStatusNot(deletada).map { it.toToolModel() }
    }
}
