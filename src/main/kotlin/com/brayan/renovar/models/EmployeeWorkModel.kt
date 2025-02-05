package com.brayan.renovar.models

import jakarta.persistence.Column
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class EmployeeWorkModel(
    val employeeId: UUID,
    val workId: UUID,
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val creationDate: LocalDateTime? = null,
    val updateData: LocalDateTime? = null,
)
