package com.brayan.renovar.models

import com.brayan.renovar.enum.EmployeeStatus
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class EmployeeModel(
    val id: UUID? = null,
    val registration: String,
    val name: String,
    val cpf: String,
    val birthDate: LocalDate,
    val functionModel: FunctionModel,
    val creationDate: LocalDateTime? = null,
    val updateDate: LocalDateTime? = null,
    val phones: String,
    val addressModel: AddressModel,
    val employeesWorks: List<EmployeeWorkModel> = mutableListOf(),
    val generalRegistration: String,
    val hourlyRate: Double,
    val employeeStatus: EmployeeStatus,
    val employeeEpis: List<EmployeeEPIModel> = mutableListOf(),
)
