package com.brayan.renovar.services.implementations

import com.brayan.renovar.api.request.AddEmployeesWorkRequest
import com.brayan.renovar.api.request.WorkUpdateRequest
import com.brayan.renovar.database.repositories.interfaces.EmployeeRepository
import com.brayan.renovar.database.repositories.interfaces.ToolRepository
import com.brayan.renovar.database.repositories.interfaces.WorkRepository
import com.brayan.renovar.enum.WorkStatus
import com.brayan.renovar.models.EmployeeWorkModel
import com.brayan.renovar.models.ToolsWorkModel
import com.brayan.renovar.models.WorkModel
import com.brayan.renovar.services.interfaces.WorkService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class WorkServiceImpl (
    val workRepository: WorkRepository,
    val employeeRepository: EmployeeRepository,
    val toolRepository: ToolRepository
): WorkService {
    override fun createWork(workModel: WorkModel): WorkModel {
        return workRepository.save(workModel)
    }

    override fun updateWork(workUpdate: WorkUpdateRequest): WorkModel {
        val work = workRepository.findById(workUpdate.id)
        val workModel = work.copy(
            companyProviding = workUpdate.companyProviding ?: work.companyProviding,
            workStatus = workUpdate.workStatus ?: work.workStatus
        )
        return workRepository.save(workModel)
    }

    override fun getWorks(): List<WorkModel> {
        return workRepository.findAll()
    }

    override fun getWorkById(workId: UUID): WorkModel {
        return workRepository.findById(workId)
    }

    override fun getWorksByStatus(status: WorkStatus): List<WorkModel> {
        return workRepository.findByWorkStatus(status)
    }

    override fun getWorksByCompanyProviding(companyProviding: String): List<WorkModel> {
        return workRepository.findByCompanyProviding(companyProviding)
    }

}