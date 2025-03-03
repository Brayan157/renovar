package com.brayan.renovar.database.repositories.springData

import com.brayan.renovar.database.entities.CreationDate
import com.brayan.renovar.database.entities.EPI
import com.brayan.renovar.database.entities.Employee
import com.brayan.renovar.database.entities.EmployeeEPI
import com.brayan.renovar.database.entities.EmployeeEPIId
import com.brayan.renovar.enum.EPIStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface EmployeeEpiSpringDataRepository:JpaRepository<EmployeeEPI, EmployeeEPIId> {
    fun findByEpiStatus(entregue: EPIStatus): List<EmployeeEPI>
    fun findByEmployee(employee: Employee): List<EmployeeEPI>
    fun findByEpi(epi: EPI): List<EmployeeEPI>
    fun findByEmployeeAndEpiStatus(employee: Employee, entregue: EPIStatus): List<EmployeeEPI>
    fun findByEmployeeAndEpiAndReturnDateIsNull(employee: Employee, epi: EPI): List<EmployeeEPI>
    @Query("SELECT e FROM EmployeeEPI e WHERE e.employee.id = :employeeId AND e.epi.id = :epiId AND e.creationDateEntity.id = :creationDateId AND e.returnDate IS NULL")
    fun findByEmployeeEpiAndCreationDate(employeeId: UUID, epiId: UUID, creationDateId: UUID): EmployeeEPI?
    fun findByEmployeeAndEpiAndCreationDateEntity(employee: Employee, epi: EPI, creation: CreationDate): EmployeeEPI
    fun findByCreationDateEntity(creation: CreationDate): EmployeeEPI
    fun findByIdEmployeeIdAndIdEpiIdAndIdCreationDateId(id: UUID, id1: UUID, id2: UUID): EmployeeEPI
}