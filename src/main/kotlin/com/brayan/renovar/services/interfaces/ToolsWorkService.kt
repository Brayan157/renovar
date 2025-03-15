package com.brayan.renovar.services.interfaces

import com.brayan.renovar.api.request.ToolsWorkResquest
import com.brayan.renovar.api.request.ToolsWorkUpdateResquest
import com.brayan.renovar.api.response.ToolsWorkResponse
import com.brayan.renovar.database.entities.ToolsWorkId
import java.util.UUID

interface ToolsWorkService {
    fun save(toolsWorkRequest: ToolsWorkResquest): ToolsWorkResponse
    fun update(toolsWorkRequest: ToolsWorkUpdateResquest): ToolsWorkResponse
    //listar todas as ferramentas emprestadas
    fun listAll(): List<ToolsWorkResponse>
    //listar todas as ferramentas emprestadas por uma obra
    fun listByWorkId(workId: UUID): List<ToolsWorkResponse>
    //listar todas as ferramentas emprestadas por uma obra com status emprestado
    fun listByWorkIdAndStatusLoaned(workId: UUID): List<ToolsWorkResponse>
    fun listByWorkIdAndStatusReturned(workId: UUID): List<ToolsWorkResponse>
    fun listByWorkIdAndToolId(toolsWorkId: ToolsWorkId): ToolsWorkResponse


}
