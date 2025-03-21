package com.brayan.renovar.database.repositories.interfaces

import com.brayan.renovar.database.entities.Tool
import com.brayan.renovar.enum.ToolStatus
import com.brayan.renovar.models.ToolModel
import java.util.UUID

interface ToolRepository {
    fun findAllById(ids: List<UUID>): List<Tool>
    fun save(toolModel: ToolModel): ToolModel
    fun findAll(): List<ToolModel>
    fun findById(id: UUID): ToolModel
    fun findByName(name: String): List<ToolModel>
    fun findByStatus(status: ToolStatus): List<ToolModel>
    fun findByStatusNot(deletada: ToolStatus): List<ToolModel>
}
