package com.brayan.renovar.api.request

import com.brayan.renovar.database.entities.CreationDate
import com.brayan.renovar.database.entities.EmployeeEPIId
import com.brayan.renovar.enum.EPIStatus
import com.brayan.renovar.models.EmployeeModel
import java.time.LocalDate
import java.util.UUID

data class ReturnEpiToEmployeeRequest (
    val employeeId: UUID,
    val epiId: UUID,
    val creationDateId: UUID,
    val quantity: Int,
    val returnDate: LocalDate
)
