package com.brayan.renovar.database.repositories.implementations

import com.brayan.renovar.database.entities.EPI
import com.brayan.renovar.database.repositories.interfaces.EPIRepository
import com.brayan.renovar.database.repositories.springData.EPISpringDataRepository
import com.brayan.renovar.database.repositories.springData.EmployeeSpringDataRepository
import com.brayan.renovar.models.EPIModel
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.UUID
@Component

class EPIRepositoryImpl(
    val epiJpaRepository: EPISpringDataRepository,
    val employeeRepository: EmployeeSpringDataRepository,
):EPIRepository {
    override fun findAllById(ids: List<UUID>): List<EPI> {
        return epiJpaRepository.findAllById(ids)
    }

    override fun save(epi: EPIModel): EPIModel {
        val employees = if (epi.employeeEpis.isNotEmpty()) {
            employeeRepository.findAllById(epi.employeeEpis.map { it.employeeId })
        } else emptyList()
        val epiEntity = EPI.of(epi, employees)
        return epiJpaRepository.save(epiEntity).toEPIModel()
    }

    override fun findById(id: UUID): EPIModel {
        return epiJpaRepository.findById(id).map { it.toEPIModel() }.orElseThrow { Exception() }
    }
    //listar todos os EPIs que a expirationDate é menor que a data atual
    override fun listExpiredEPIs(date:LocalDate): List<EPIModel> {
        return epiJpaRepository.findByExpirationDateBefore(date).map { it.toEPIModel() }
    }

    override fun findAll(): List<EPIModel> {
        return epiJpaRepository.findAll().map { it.toEPIModel() }
    }

    override fun findByName(name: String): List<EPIModel> {
        return epiJpaRepository.findByNameContainingIgnoreCase(name).map { it.toEPIModel() }
    }

    override fun listNotExpiredEPIs(currentDate: LocalDate?): List<EPIModel> {
        return epiJpaRepository.findByExpirationDateAfter(currentDate).map { it.toEPIModel() }
    }

}
