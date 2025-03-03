package com.brayan.renovar.database.repositories.springData

import com.brayan.renovar.database.entities.Employee
import com.brayan.renovar.database.entities.EmployeeWork
import com.brayan.renovar.database.entities.EmployeeWorkKey
import com.brayan.renovar.database.entities.Work
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface EmployeeWorkSpringDataRepository : JpaRepository<EmployeeWork, EmployeeWorkKey> {
    fun findByEndDateIsNull(): List<EmployeeWork>
    fun findByEndDateIsNotNull(): List<EmployeeWork>
    fun findByWorkId(workId: UUID): List<EmployeeWork>
    fun findByEmployeeAndEndDateIsNull(employee: Employee): List<EmployeeWork>
    fun findByWork(work: Work): List<EmployeeWork>

}
