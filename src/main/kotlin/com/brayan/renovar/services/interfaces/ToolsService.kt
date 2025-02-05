package com.brayan.renovar.services.interfaces

import com.brayan.renovar.api.request.ToolUpdateRequest
import com.brayan.renovar.enum.ToolStatus
import com.brayan.renovar.models.ToolModel
import java.util.UUID

interface ToolsService {
    fun save(toolModel: ToolModel): ToolModel
    fun findAll():List<ToolModel>
    fun findById(id: UUID): ToolModel
    fun findByName(name: String): List<ToolModel>
    fun findByStatus(status: ToolStatus): List<ToolModel>
    fun updateStatus(toolUpdateRequest: ToolUpdateRequest): ToolModel
    fun deleteById(id: UUID): Boolean
    fun findByStatusNot(deletada: ToolStatus): List<ToolModel>

}
