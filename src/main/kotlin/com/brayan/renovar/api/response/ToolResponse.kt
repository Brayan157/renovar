package com.brayan.renovar.api.response

import com.brayan.renovar.enum.ToolStatus
import com.brayan.renovar.models.ToolsEmployeesModel
import com.brayan.renovar.models.ToolsWorkModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class ToolResponse(
    val id: UUID,
    val name: String,
    val purchaseDate: LocalDate,
    val unitValue: Double,
    val toolStatus: ToolStatus,
    val quantity: Int,
    val creationDate : LocalDateTime? = null,
    val updateDate : LocalDateTime? = null,
)
