package com.brayan.renovar.services.interfaces

import com.brayan.renovar.api.request.AddEmployeesWorkRequest
import com.brayan.renovar.api.request.ReturnEmployeeToWork
import com.brayan.renovar.api.response.EmployeeWorkResponse
import com.brayan.renovar.database.entities.EmployeeWorkKey
import java.util.UUID

interface EmployeeWorkService {
    fun saveEmployeeWork(addEmployeesWorkRequest: AddEmployeesWorkRequest): EmployeeWorkResponse
    fun updateEmployeeWork(returnEmployeeToWork: ReturnEmployeeToWork): EmployeeWorkResponse
    fun getEmployeeWork(): List<EmployeeWorkResponse>
    fun getEmployeeWorkById(id: EmployeeWorkKey): EmployeeWorkResponse
    fun getEmployeeWorkWorking(): List<EmployeeWorkResponse>
    fun getEmployeeWorkNotWorking(): List<EmployeeWorkResponse>
    fun getEmployeeWorkByEmployeeId(employeeId: UUID): List<EmployeeWorkResponse>
    fun getEmployeeWorkByWorkId(workId: UUID): List<EmployeeWorkResponse>
}
