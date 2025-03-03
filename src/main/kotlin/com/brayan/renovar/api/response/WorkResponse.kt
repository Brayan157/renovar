package com.brayan.renovar.api.response

import com.brayan.renovar.enum.WorkStatus
import com.brayan.renovar.models.AddressModel
import com.brayan.renovar.models.EmployeeWorkModel
import com.brayan.renovar.models.ToolsWorkModel
import java.time.LocalDateTime
import java.util.UUID

data class WorkResponse(
    val id: UUID,
    val companyProviding: String,
    val cnpj: String,
    val workStatus: WorkStatus,
    val address: AddressModel,
    val creationDate: LocalDateTime? = null,
    val updateDate: LocalDateTime? = null,
)
