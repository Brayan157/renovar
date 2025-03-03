package com.brayan.renovar.models

import com.brayan.renovar.api.response.EmployeeResponse
import com.brayan.renovar.enum.EmployeeStatus
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class EmployeeModel(
    val id: UUID? = null,
    val registration: Int? = null,
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
    val toolsEmployee: List<ToolsEmployeesModel> = mutableListOf()
){
    fun toResponse() = EmployeeResponse(
        id = id!!,
        registration = registration!!,
        name = name,
        cpf = cpf,
        birthDate = birthDate,
        phones = phones,
        addressModel = addressModel,
        generalRegistration = generalRegistration,
        hourlyRate = hourlyRate,
        function = functionModel.function,
        status = employeeStatus
    )
}
