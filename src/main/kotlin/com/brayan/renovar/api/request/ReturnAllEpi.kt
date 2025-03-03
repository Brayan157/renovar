package com.brayan.renovar.api.request

import com.brayan.renovar.models.EmployeeModel
import java.time.LocalDate

data class ReturnAllEpi(
    val employeeModel: EmployeeModel,
    val returnDate: LocalDate
)
