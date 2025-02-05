package com.brayan.renovar.api.request

import com.brayan.renovar.enum.ToolStatus
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class ToolUpdateRequest(
    val id: UUID,
    val toolStatus: ToolStatus? = null
)
