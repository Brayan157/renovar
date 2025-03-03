package com.brayan.renovar.api.request

import jakarta.persistence.Column
import java.util.UUID

data class EmployeeEpiIdRequest(
    val employeeId: UUID,
    val epiId: UUID,
    val creationDateId: UUID
)
