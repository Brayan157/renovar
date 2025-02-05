package com.brayan.renovar.database.repositories.implementations

import com.brayan.renovar.database.entities.Work
import com.brayan.renovar.database.repositories.interfaces.WorkRepository
import com.brayan.renovar.database.repositories.springData.EmployeeSpringDataRepository
import com.brayan.renovar.database.repositories.springData.ToolSpringDataRepository
import com.brayan.renovar.database.repositories.springData.WorkSpringDataRepository
import com.brayan.renovar.enum.WorkStatus
import com.brayan.renovar.models.WorkModel
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class WorkRepositoryImpl(
    val workJpaRepository: WorkSpringDataRepository,
    val employeeRepository: EmployeeSpringDataRepository,
    val toolRepository: ToolSpringDataRepository
): WorkRepository {
    override fun save(work: WorkModel): WorkModel {
        val employees = if (work.employeesWorks.isEmpty()) {
            employeeRepository.findAllById(work.employeesWorks.map { it.employeeId })
        } else emptyList()
        val tools = if (work.toolsWorks.isEmpty()) {
            toolRepository.findAllById(work.toolsWorks.map { it.toolsId })
        } else emptyList()
        val work = Work.of(work, employees, tools)
        return workJpaRepository.save(work).toWorkModel()
    }

    override fun findAll(): List<WorkModel> {
        return workJpaRepository.findAll().map { it.toWorkModel() }
    }

    override fun findById(id: UUID): WorkModel {
        return workJpaRepository.findById(id).map { it.toWorkModel() }.orElseThrow { ChangeSetPersister.NotFoundException() }
    }



    override fun findAllById(worksIds: List<UUID>): List<Work> {
        return workJpaRepository.findAllById(worksIds)
    }

    override fun findByWorkStatus(status: WorkStatus): List<WorkModel> {
        return workJpaRepository.findByWorkStatus(status).map { it.toWorkModel() }
    }

    override fun findByCompanyProviding(companyProviding: String): List<WorkModel> {
        return workJpaRepository.findByCompanyProvidingContainingIgnoreCase(companyProviding).map { it.toWorkModel() }
    }
}