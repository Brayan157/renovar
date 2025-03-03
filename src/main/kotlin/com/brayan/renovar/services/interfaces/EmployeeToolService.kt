package com.brayan.renovar.services.interfaces

import com.brayan.renovar.api.request.EmployeeToolRequest
import com.brayan.renovar.api.request.EmployeeToolUpdateRequest
import com.brayan.renovar.api.response.EmployeeToolResponse
import java.util.UUID

interface EmployeeToolService {
    fun save(employeeToolRequest: EmployeeToolRequest): EmployeeToolResponse
    fun update(employeeToolUpdateRequest: EmployeeToolUpdateRequest): EmployeeToolResponse
    fun listAll(): List<EmployeeToolResponse>
    fun listByEmployeeId(employeeId: UUID): List<EmployeeToolResponse>
    fun listByEmployeeIdAndStatusLoaned(employeeId: UUID): List<EmployeeToolResponse>
    fun listByEmployeeIdAndStatusReturned(employeeId: UUID): List<EmployeeToolResponse>

}
