package com.brayan.renovar.services.interfaces

import com.brayan.renovar.api.request.ToolUpdateRequest
import com.brayan.renovar.api.request.ToolUpdateStatusRequest
import com.brayan.renovar.api.response.ToolResponse
import com.brayan.renovar.enum.ToolStatus
import com.brayan.renovar.models.ToolModel
import java.util.UUID

interface ToolsService {
    fun save(toolModel: ToolModel): ToolResponse
    fun findAll():List<ToolResponse>
    fun findById(id: UUID): ToolResponse
    fun findByName(name: String): List<ToolResponse>
    fun findByStatus(status: ToolStatus): List<ToolResponse>
    fun deleteById(id: UUID): Boolean
    fun findByStatusNot(deletada: ToolStatus): List<ToolResponse>
    fun update(toolUpdade: ToolUpdateRequest): ToolResponse

}
