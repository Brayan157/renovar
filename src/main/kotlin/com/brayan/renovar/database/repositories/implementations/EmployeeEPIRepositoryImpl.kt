package com.brayan.renovar.database.repositories.implementations

import com.brayan.renovar.api.response.EmployeeEpiResponse
import com.brayan.renovar.database.entities.EmployeeEPI
import com.brayan.renovar.database.entities.EmployeeEPIId
import com.brayan.renovar.database.repositories.interfaces.EmployeeEPIRepository
import com.brayan.renovar.database.repositories.springData.CreationSpringDataRepository
import com.brayan.renovar.database.repositories.springData.EPISpringDataRepository
import com.brayan.renovar.database.repositories.springData.EmployeeEpiSpringDataRepository
import com.brayan.renovar.database.repositories.springData.EmployeeSpringDataRepository
import com.brayan.renovar.enum.EPIStatus
import com.brayan.renovar.models.EmployeeEPIModel
import org.springframework.stereotype.Component
import java.lang.RuntimeException
import java.util.UUID

@Component
class EmployeeEPIRepositoryImpl(
    private val employeeEpiSpringDataRepository: EmployeeEpiSpringDataRepository,
    private val employeeSpringDataRepository: EmployeeSpringDataRepository,
    private val epiSpringDataRepository: EPISpringDataRepository,
    private val creationJpaRepository: CreationSpringDataRepository
): EmployeeEPIRepository {
    override fun save(employeeEpiModel: EmployeeEPIModel): EmployeeEpiResponse {
        val employee = employeeSpringDataRepository.findById(employeeEpiModel.employeeId).get()
        val epi = epiSpringDataRepository.findById(employeeEpiModel.epiId).get()
        val creationJpaRepository = creationJpaRepository.findById(employeeEpiModel.creationDateId).get()
        val employeeEPI = EmployeeEPI(
            id = EmployeeEPIId(
                employeeId = employee.id,
                epiId = epi.id,
                creationDateId = creationJpaRepository.id
            ),
            employee = employee,
            epi = epi,
            quantity = employeeEpiModel.quantity,
            deliveryDate = employeeEpiModel.deliveryDate,
            returnDate = employeeEpiModel.returnDate,
            epiStatus = employeeEpiModel.epiStatus,
            reason = employeeEpiModel.reason,
            creationDateEntity = creationJpaRepository,
            creationDate = employeeEpiModel.creationDate,
            updateDate = employeeEpiModel.updateDate
        )
        return employeeEpiSpringDataRepository.save(employeeEPI).toEmployeeEPIResponse()

    }

    override fun findAll(): List<EmployeeEPI> {
        return employeeEpiSpringDataRepository.findAll()
    }

    override fun findById(id: EmployeeEPIId): EmployeeEPIModel {
        return employeeEpiSpringDataRepository.findById(id).orElseThrow { RuntimeException("EPI não encontrado para o funcionário com ID: $id") }.toEmployeeEPIModel()
    }

    override fun findByStatus(entregue: EPIStatus): List<EmployeeEpiResponse> {
        return employeeEpiSpringDataRepository.findByEpiStatus(entregue).map { it.toEmployeeEPIResponse() }
    }

    override fun findByEmployeeId(employeeId: UUID): List<EmployeeEPI> {
        val employee = employeeSpringDataRepository.findById(employeeId).get()
        return employeeEpiSpringDataRepository.findByEmployee(employee)
    }

    override fun findByEpiId(epiId: UUID): List<EmployeeEPI> {
        val epi = epiSpringDataRepository.findById(epiId).get()
        return employeeEpiSpringDataRepository.findByEpi(epi)
    }

    override fun findByEmployeeIdAndEPIStatus(employeeId: UUID): List<EmployeeEPI> {
        val employee = employeeSpringDataRepository.findById(employeeId).get()
        return employeeEpiSpringDataRepository.findByEmployeeAndEpiStatus(employee, EPIStatus.ENTREGUE)
    }

    override fun existByEmployeeAndEpiAndReturnDateIsNull(employeeId: UUID, epiId: UUID): List<EmployeeEPI> {
        val employee = employeeSpringDataRepository.findById(employeeId).get()
        val epi = epiSpringDataRepository.findById(epiId).get()
        return employeeEpiSpringDataRepository.findByEmployeeAndEpiAndReturnDateIsNull(employee, epi)
    }

    override fun findByEmployeeAndEpiAndCreationDate(id: EmployeeEPIId): EmployeeEPI? {
        val employee = employeeSpringDataRepository.findById(id.employeeId!!).get()
        val epi = epiSpringDataRepository.findById(id.epiId!!).get()
        val creation = creationJpaRepository.findById(id.creationDateId!!).get()
        return employeeEpiSpringDataRepository.findByEmployeeEpiAndCreationDate(employee.id!!, epi.id!!, creation.id!!)
    }

    override fun findBycreationDateEntity(creationDateId: UUID): EmployeeEPI {
        val creation = creationJpaRepository.findById(creationDateId).get()
        return employeeEpiSpringDataRepository.findByCreationDateEntity(creation)
    }

    override fun findByIdEmployeeIdAndIdEpiIdAndIdCreationDateId(
        employeeId: UUID,
        epiId: UUID,
        creationDateId: UUID,
    ): EmployeeEPI {
        val employee = employeeSpringDataRepository.findById(employeeId).get()
        val epi = epiSpringDataRepository.findById(epiId).get()
        val creation = creationJpaRepository.findById(creationDateId).get()
        return employeeEpiSpringDataRepository.findByIdEmployeeIdAndIdEpiIdAndIdCreationDateId(employee.id!!, epi.id!!, creation.id!!)
    }
}