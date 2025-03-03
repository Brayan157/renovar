package com.brayan.renovar.database.repositories.springData

import com.brayan.renovar.database.entities.ToolsWork
import com.brayan.renovar.database.entities.ToolsWorkId
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ToolsWorkSpringDataRepository:JpaRepository<ToolsWork, ToolsWorkId> {
    fun findByToolIdAndExitDateIsNull(toolId: UUID): List<ToolsWork>

}
