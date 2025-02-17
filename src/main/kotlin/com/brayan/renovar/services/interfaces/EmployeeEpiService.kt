package com.brayan.renovar.services.interfaces

import com.brayan.renovar.api.request.AddEpiToEmployeeRequest
import com.brayan.renovar.api.request.ReturnEpiToEmployeeRequest
import com.brayan.renovar.api.response.EmployeeEpiResponse
import com.brayan.renovar.models.EmployeeEPIModel

interface EmployeeEpiService {
    fun addEpiToEmployee(addEpiToEmployeeRequest: AddEpiToEmployeeRequest): EmployeeEpiResponse
    fun ReturnEpiToEmployee(returnEpiToEmployeeRequest: ReturnEpiToEmployeeRequest): EmployeeEpiResponse
    fun getEmployeeEpi(): EmployeeEPIModel
}
