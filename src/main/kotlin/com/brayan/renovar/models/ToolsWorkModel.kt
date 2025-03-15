package com.brayan.renovar.models

import com.brayan.renovar.api.response.ToolResponse
import com.brayan.renovar.api.response.ToolsWorkResponse
import com.brayan.renovar.api.response.WorkResponse
import com.brayan.renovar.database.entities.Employee
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class ToolsWorkModel(
    val toolsId: UUID,
    val workId: UUID,
    val reason: String,
    val entryDate:LocalDate,
    val exitDate:LocalDate? = null,
    val creationDate:LocalDateTime? = null,
    val updateDate: LocalDateTime? = null,
    val creationDateId: UUID
){
    fun toResponse(tool: ToolResponse, work:WorkResponse) = ToolsWorkResponse(
        tool = tool,
        workResponse = work,
        reason = reason,
        entryDate = entryDate,
        exitDate = exitDate,
        creationDate = creationDate,
        updateDate = updateDate,
        creationDateId = creationDateId
    )
}
