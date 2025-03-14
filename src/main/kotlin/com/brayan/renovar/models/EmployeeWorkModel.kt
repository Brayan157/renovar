package com.brayan.renovar.models

import com.brayan.renovar.api.response.EmployeeResponse
import com.brayan.renovar.api.response.EmployeeWorkResponse
import com.brayan.renovar.api.response.WorkResponse
import com.brayan.renovar.database.entities.CreationDate
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
    val creationDateId: UUID
){
    fun toEmployeeWorkResponse(work:WorkResponse, employee:EmployeeResponse, creationDate: CreationDate) = EmployeeWorkResponse(
        workResponse = work,
        startDate = startDate!!,
        endDate = endDate,
        creationDate = creationDate,
        employeeResponse = employee
    )
}
