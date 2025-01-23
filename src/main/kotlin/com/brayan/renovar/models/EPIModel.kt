package com.brayan.renovar.models

import com.brayan.renovar.database.entities.EmployeeEPI
import java.time.LocalDateTime
import java.util.UUID

data class EPIModel(
    val id: UUID? = null,
    val name:String,
    val approvalCertificate: String,
    val quantity: Int,
    val unitValue: Double,
    val manufacturingDate: String,
    val expirationDate: String,
    val tag:String,
    val lot:String,
    val creationDate: LocalDateTime? = null,
    val updateDate: LocalDateTime? = null,
    val employeeEpis: List<EmployeeEPIModel> = mutableListOf()
)
