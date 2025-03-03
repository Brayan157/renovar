package com.brayan.renovar.api.request

import com.brayan.renovar.database.entities.EmployeeWorkKey
import java.time.LocalDate
import java.util.UUID

data class ReturnEmployeeToWork(
    val id: EmployeeWorkKey,
    val returnDate: LocalDate
)
