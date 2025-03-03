package com.brayan.renovar.api.response

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID
import javax.print.attribute.standard.JobStateReason

data class ToolsWorkResponse (
    val tool: ToolResponse,
    val workResponse: WorkResponse,
    val reason: String,
    val entryDate: LocalDate,
    val exitDate: LocalDate? = null,
    val creationDate: LocalDateTime? = null,
    val updateDate: LocalDateTime? = null,
    val creationDateId: UUID

)
