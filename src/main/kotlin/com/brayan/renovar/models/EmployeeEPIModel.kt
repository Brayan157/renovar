package com.brayan.renovar.models

import java.util.UUID

data class EmployeeEPIModel(
    val employeeId: UUID,
    val epiId: UUID,
    val quantity: Int,
    val deliveryDate: String,
    val reason: String
)
