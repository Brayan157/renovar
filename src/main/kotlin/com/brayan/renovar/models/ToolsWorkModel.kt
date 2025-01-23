package com.brayan.renovar.models

import com.brayan.renovar.database.entities.Employee
import java.time.LocalDateTime
import java.util.UUID

data class ToolsWorkModel(
    val toolsId: UUID,
    val workId: UUID,
    val reason: String,
    val entryDate:String,
    val exitDate:String,
    val creationDate:LocalDateTime? = null,
    val updateDate: LocalDateTime? = null
)
