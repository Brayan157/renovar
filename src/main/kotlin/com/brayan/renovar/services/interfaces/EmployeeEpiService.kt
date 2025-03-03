package com.brayan.renovar.services.interfaces

import com.brayan.renovar.api.request.AddEpiToEmployeeRequest
import com.brayan.renovar.api.request.ReturnAllEpi
import com.brayan.renovar.api.request.ReturnEpiToEmployeeRequest
import com.brayan.renovar.api.response.EmployeeEpiResponse
import com.brayan.renovar.database.entities.EmployeeEPI
import com.brayan.renovar.database.entities.EmployeeEPIId
import com.brayan.renovar.models.EmployeeEPIModel
import java.util.UUID

interface EmployeeEpiService {
    fun addEpiToEmployee(addEpiToEmployeeRequest: AddEpiToEmployeeRequest): EmployeeEpiResponse
    fun returnEpiToEmployee(returnEpiToEmployeeRequest: ReturnEpiToEmployeeRequest): EmployeeEPIModel
    fun getEmployeeEpi(): List<EmployeeEpiResponse>
    fun getEmployeeEpiId(id: EmployeeEPIId): EmployeeEpiResponse
    fun getEmployeeEpiDelivered(): List<EmployeeEpiResponse>
    fun getEmployeeEpiReturned(): List<EmployeeEpiResponse>
    fun returnAllEpiToEmployee(returnAllEpi: ReturnAllEpi): List<EmployeeEpiResponse>
    fun getEmployeeEpiByEmployeeId(employeeId: UUID): List<EmployeeEpiResponse>
    fun getEmployeeEpiByEpiId(epiId: UUID): List<EmployeeEpiResponse>
    fun getEmployeeEpiDeliveredByEmployeeId(employeeId: UUID): List<EmployeeEpiResponse>
    fun getEmployeeEpiModel(): List<EmployeeEPI>

}
