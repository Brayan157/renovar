package com.brayan.renovar.database.repositories.implementations

import com.brayan.renovar.database.entities.CreationDate
import com.brayan.renovar.database.entities.EmployeeEPI
import com.brayan.renovar.database.entities.EmployeeEPIId
import com.brayan.renovar.database.repositories.interfaces.EmployeeEPIRepository
import com.brayan.renovar.database.repositories.springData.CreationSpringDataRepository
import com.brayan.renovar.database.repositories.springData.EPISpringDataRepository
import com.brayan.renovar.database.repositories.springData.EmployeeEpiSpringDataRepository
import com.brayan.renovar.database.repositories.springData.EmployeeSpringDataRepository
import com.brayan.renovar.models.EmployeeEPIModel
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class EmployeeEPIRepositoryImpl(
    private val employeeEpiSpringDataRepository: EmployeeEpiSpringDataRepository,
    private val employeeSpringDataRepository: EmployeeSpringDataRepository,
    private val epiSpringDataRepository: EPISpringDataRepository,
    private val creationJpaRepository: CreationSpringDataRepository
): EmployeeEPIRepository {
    override fun save(employeeEpiModel: EmployeeEPIModel): EmployeeEPI {
        val employeeEPI = EmployeeEPI(
            id = EmployeeEPIId(
                employeeId = employeeEpiModel.employeeId,
                epiId = employeeEpiModel.epiId,
                creationDateId = employeeEpiModel.creationDateId
            ),
            employee = employeeSpringDataRepository.findById(employeeEpiModel.employeeId).get(),
            epi = epiSpringDataRepository.findById(employeeEpiModel.epiId).get(),
            quantity = employeeEpiModel.quantity,
            deliveryDate = employeeEpiModel.deliveryDate,
            returnDate = employeeEpiModel.returnDate,
            epiStatus = employeeEpiModel.epiStatus,
            reason = employeeEpiModel.reason,
            updateDate = employeeEpiModel.updateDate,
            creationDateEntity = creationJpaRepository.findById(employeeEpiModel.creationDateId).get(),
            creationDate = employeeEpiModel.creationDate
        )
        return employeeEpiSpringDataRepository.save(employeeEPI)

    }
}