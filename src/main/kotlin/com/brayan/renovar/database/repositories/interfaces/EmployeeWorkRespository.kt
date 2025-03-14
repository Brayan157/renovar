package com.brayan.renovar.database.repositories.interfaces

import com.brayan.renovar.database.entities.EmployeeWork
import com.brayan.renovar.database.entities.EmployeeWorkKey
import com.brayan.renovar.models.EmployeeWorkModel
import java.util.UUID

interface EmployeeWorkRespository {
    fun save(employeeWork: EmployeeWorkModel): EmployeeWorkModel
    fun findById(id: EmployeeWorkKey): EmployeeWorkModel
    fun findAll(): List<EmployeeWorkModel>
    fun findByEndDateIsNull(): List<EmployeeWorkModel>
    fun findByEndDateIsNotNull(): List<EmployeeWorkModel>
    fun findByEmployeeIdAndEndDateIsNull(employeeId: UUID): List<EmployeeWorkModel>
    fun findByWorkId(workId: UUID): List<EmployeeWorkModel>

}
