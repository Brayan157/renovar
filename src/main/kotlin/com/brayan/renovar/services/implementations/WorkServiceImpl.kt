package com.brayan.renovar.services.implementations

import com.brayan.renovar.api.request.AddEmployeesWorkRequest
import com.brayan.renovar.api.request.WorkUpdateRequest
import com.brayan.renovar.api.response.WorkResponse
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
    val workRepository: WorkRepository
): WorkService {
    override fun createWork(workModel: WorkModel): WorkResponse {
        return workRepository.save(workModel).toResponse()
    }

    override fun updateWork(workUpdate: WorkUpdateRequest): WorkResponse {
        val work = workRepository.findById(workUpdate.id)
        val workModel = work.copy(
            companyProviding = workUpdate.companyProviding ?: work.companyProviding,
            workStatus = workUpdate.workStatus ?: work.workStatus
        )
        return workRepository.save(workModel).toResponse()
    }

    override fun getWorks(): List<WorkResponse> {
        return workRepository.findAll().map { it.toResponse() }
    }

    override fun getWorkById(workId: UUID): WorkResponse {
        return workRepository.findById(workId).toResponse()
    }

    override fun getWorksByStatus(status: WorkStatus): List<WorkResponse> {
        return workRepository.findByWorkStatus(status).map { it.toResponse() }
    }

    override fun getWorksByCompanyProviding(companyProviding: String): List<WorkResponse> {
        return workRepository.findByCompanyProviding(companyProviding).map { it.toResponse() }
    }

}