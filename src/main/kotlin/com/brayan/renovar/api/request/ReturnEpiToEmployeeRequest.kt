package com.brayan.renovar.api.request

import com.brayan.renovar.enum.EPIStatus
import com.brayan.renovar.models.EmployeeModel
import java.time.LocalDate
import java.util.UUID

data class ReturnEpiToEmployeeRequest (
    val employee: EmployeeModel,
    val epiId: UUID,
    val quantity: Int,
    val returnDate: LocalDate,
    val epiStatus: EPIStatus = EPIStatus.DEVOLVIDO,
    val returnToStock: Boolean
)
