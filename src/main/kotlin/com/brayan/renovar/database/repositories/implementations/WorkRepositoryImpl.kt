package com.brayan.renovar.database.repositories.implementations

import com.brayan.renovar.database.entities.Employee
import com.brayan.renovar.database.entities.Tool
import com.brayan.renovar.database.entities.Work
import com.brayan.renovar.database.repositories.interfaces.EmployeeRepository
import com.brayan.renovar.database.repositories.interfaces.ToolRepository
import com.brayan.renovar.database.repositories.interfaces.WorkRepository
import com.brayan.renovar.database.repositories.springData.WorkSpringDataRepository
import com.brayan.renovar.enum.WorkStatus
import com.brayan.renovar.models.WorkModel
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class WorkRepositoryImpl(
    val workJpaRepository: WorkSpringDataRepository,
    val employeeRepository: EmployeeRepository,
    val toolRepository: ToolRepository
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
        TODO("Not yet implemented")
    }

    override fun findById(id: UUID): WorkModel {
        TODO("Not yet implemented")
    }

    override fun findByStatus(status: WorkStatus): List<WorkModel> {
        TODO("Not yet implemented")
    }

    override fun findAllById(worksIds: List<UUID>): List<Work> {
        return workJpaRepository.findAllById(worksIds)
    }
}