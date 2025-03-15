package com.brayan.renovar.database.repositories.interfaces

import com.brayan.renovar.database.entities.ToolsWork
import com.brayan.renovar.database.entities.ToolsWorkId
import com.brayan.renovar.models.ToolsWorkModel
import java.util.UUID

interface ToolsWorkRepository {
    fun findByToolIdAndExitDateIsNull(toolId: UUID): List<ToolsWorkModel>
    fun save(toolsWork: ToolsWorkModel): ToolsWorkModel
    fun findById(id: ToolsWorkId): ToolsWorkModel
    fun findAll(): List<ToolsWorkModel>
    fun findByWorkId(workId: UUID): List<ToolsWorkModel>
    fun findByWorkIdAndExitDateIsNull(workId: UUID): List<ToolsWorkModel>
    fun findByWorkIdAndExitDateIsNotNull(workId: UUID): List<ToolsWorkModel>

}
