package com.brayan.renovar.api.request

import java.time.LocalDate
import java.util.UUID

data class ToolsWorkUpdateResquest (
    val toolsId: UUID,
    val workId: UUID,
    val creationDateId: UUID,
    val exitDate: LocalDate
)

