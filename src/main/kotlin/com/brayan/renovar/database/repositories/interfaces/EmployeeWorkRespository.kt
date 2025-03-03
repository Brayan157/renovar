package com.brayan.renovar.database.repositories.interfaces

import com.brayan.renovar.database.entities.EmployeeWork
import com.brayan.renovar.database.entities.EmployeeWorkKey
import com.brayan.renovar.models.EmployeeWorkModel
import java.util.UUID

interface EmployeeWorkRespository {
    fun save(employeeWork: EmployeeWorkModel): EmployeeWork
    fun findById(id: EmployeeWorkKey): EmployeeWork
    fun findAll(): List<EmployeeWork>
    fun findByEndDateIsNull(): List<EmployeeWork>
    fun findByEndDateIsNotNull(): List<EmployeeWork>
    fun findByEmployeeIdAndEndDateIsNull(employeeId: UUID): List<EmployeeWork>
    fun findByWorkId(workId: UUID): List<EmployeeWork>

}
