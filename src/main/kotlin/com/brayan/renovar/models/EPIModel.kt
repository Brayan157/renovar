package com.brayan.renovar.models

import com.brayan.renovar.api.response.EpiResponse
import com.brayan.renovar.database.entities.EmployeeEPI
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class EPIModel(
    val id: UUID? = null,
    val name:String,
    val approvalCertificate: String? = null,
    val quantity: Int,
    val unitValue: Double? = null,
    val manufacturingDate: LocalDate,
    val expirationDate: LocalDate? = null,
    val tag:String? = null,
    val lot:String? = null,
    val creationDate: LocalDateTime? = null,
    val updateDate: LocalDateTime? = null,
    val employeeEpis: List<EmployeeEPIModel> = mutableListOf()
){
    fun toResponse() = EpiResponse(
        id = id!!,
        name = name,
        approvalCertificate = approvalCertificate,
        quantity = quantity,
        unitValue = unitValue,
        manufacturingDate = manufacturingDate,
        expirationDate = expirationDate,
        tag = tag,
        lot = lot
    )
}
