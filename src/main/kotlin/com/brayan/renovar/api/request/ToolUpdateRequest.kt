package com.brayan.renovar.api.request

import com.brayan.renovar.enum.ToolStatus
import com.brayan.renovar.models.ToolsEmployeesModel
import com.brayan.renovar.models.ToolsWorkModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class ToolUpdateRequest (
    val id: UUID,
    val name: String? = null,
    val purchaseDate: LocalDate? = null,
    val unitValue: Double? = null,
    val toolStatus: ToolStatus? = null,
    val quantity: Int? = null
)
