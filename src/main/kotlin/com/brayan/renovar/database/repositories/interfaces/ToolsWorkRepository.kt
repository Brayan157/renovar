package com.brayan.renovar.database.repositories.interfaces

import com.brayan.renovar.database.entities.ToolsWork
import com.brayan.renovar.database.entities.ToolsWorkId
import com.brayan.renovar.models.ToolsWorkModel
import java.util.UUID

interface ToolsWorkRepository {
    fun findByToolIdAndExitDateIsNull(toolId: UUID): List<ToolsWorkModel>
    fun save(toolsWork: ToolsWorkModel): ToolsWork
    fun findById(id: ToolsWorkId): ToolsWork

}
