package com.brayan.renovar.api.request

import java.time.LocalDate
import java.util.UUID

data class ToolsWorkResquest (
    val toolsId: UUID,
    val workId: UUID,
    val reason: String,
    val entryDate: LocalDate
)
