package com.brayan.renovar.database.repositories.implementations

import com.brayan.renovar.api.response.EpiResponse
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

    override fun save(epi: EPIModel): EpiResponse {
        val employees = if (epi.employeeEpis.isNotEmpty()) {
            employeeRepository.findAllById(epi.employeeEpis.map { it.employeeId })
        } else emptyList()
        val epiEntity = EPI.of(epi, employees)
        return epiJpaRepository.save(epiEntity).toEpiResponse()
    }

    override fun findById(id: UUID): EPIModel {
        return epiJpaRepository.findById(id).map { it.toEPIModel() }.orElseThrow { Exception() }
    }
    //listar todos os EPIs que a expirationDate é menor que a data atual
    override fun listExpiredEPIs(date:LocalDate): List<EpiResponse> {
        return epiJpaRepository.findByExpirationDateBefore(date).map { it.toEpiResponse() }
    }

    override fun findAll(): List<EpiResponse> {
        return epiJpaRepository.findAll().map { it.toEpiResponse() }
    }

    override fun findByName(name: String): List<EpiResponse> {
        return epiJpaRepository.findByNameContainingIgnoreCase(name).map { it.toEpiResponse() }
    }

    override fun listNotExpiredEPIs(currentDate: LocalDate?): List<EpiResponse> {
        return epiJpaRepository.findByExpirationDateAfter(currentDate).map { it.toEpiResponse() }
    }

}
