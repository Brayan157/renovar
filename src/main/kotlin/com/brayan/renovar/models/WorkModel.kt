package com.brayan.renovar.models

import com.brayan.renovar.enum.WorkStatus
import java.time.LocalDateTime
import java.util.UUID

data class WorkModel(
    val id: UUID? = null,
    val companyProviding: String,
    val cnpj: String,
    val workStatus: WorkStatus,
    val creationDate: LocalDateTime?,
    val updateDate: LocalDateTime?,
    val address: AddressModel,
    val employeesWorks: List<EmployeeWorkModel> = mutableListOf(),
    val toolsWorks: List<ToolsWorkModel> = mutableListOf()

)