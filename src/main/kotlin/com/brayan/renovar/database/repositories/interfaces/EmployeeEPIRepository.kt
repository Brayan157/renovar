package com.brayan.renovar.database.repositories.interfaces

import com.brayan.renovar.api.response.EmployeeEpiResponse
import com.brayan.renovar.database.entities.EmployeeEPI
import com.brayan.renovar.database.entities.EmployeeEPIId
import com.brayan.renovar.enum.EPIStatus
import com.brayan.renovar.models.EmployeeEPIModel
import java.util.UUID

interface EmployeeEPIRepository {
    fun save(employeeEpiModel: EmployeeEPIModel): EmployeeEpiResponse
    fun findAll(): List<EmployeeEPI>
    fun findById(id: EmployeeEPIId): EmployeeEPIModel
    fun findByStatus(entregue: EPIStatus): List<EmployeeEpiResponse>
    fun findByEmployeeId(employeeId: UUID): List<EmployeeEPI>
    fun findByEpiId(epiId: UUID): List<EmployeeEPI>
    fun findByEmployeeIdAndEPIStatus(employeeId: UUID): List<EmployeeEPI>
    fun existByEmployeeAndEpiAndReturnDateIsNull(employeeId: UUID, epiId: UUID): List<EmployeeEPI>
    fun findByEmployeeAndEpiAndCreationDate(id: EmployeeEPIId): EmployeeEPI?
    fun findBycreationDateEntity(creationDateId: UUID): EmployeeEPI
    fun findByIdEmployeeIdAndIdEpiIdAndIdCreationDateId(employeeId: UUID, epiId: UUID, creationDateId: UUID): EmployeeEPI

}
