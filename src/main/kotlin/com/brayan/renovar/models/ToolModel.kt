package com.brayan.renovar.models

import com.brayan.renovar.enum.ToolStatus
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class ToolModel(
    val id: UUID? = null,
    val name: String,
    val purchaseDate: LocalDate,
    val unitValue: Double,
    val creationDate: LocalDateTime? = null,
    val updateDate: LocalDateTime? = null,
    val toolStatus: ToolStatus,
    val toolsWorks: List<ToolsWorkModel> = mutableListOf(),
    val toolsEmployees: List<ToolsEmployeesModel> = mutableListOf(),
    val quantity: Int
)
