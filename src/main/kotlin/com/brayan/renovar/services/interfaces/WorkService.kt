package com.brayan.renovar.services.interfaces

import com.brayan.renovar.api.request.AddEmployeesWorkRequest
import com.brayan.renovar.api.request.WorkUpdateRequest
import com.brayan.renovar.api.response.WorkResponse
import com.brayan.renovar.enum.WorkStatus
import com.brayan.renovar.models.WorkModel
import java.util.UUID

interface WorkService {
    fun createWork(workModel: WorkModel): WorkResponse
    fun updateWork(workUpdate: WorkUpdateRequest): WorkResponse
    fun getWorks(): List<WorkResponse>
    fun getWorkById(workId: UUID): WorkResponse
    fun getWorksByStatus(status: WorkStatus): List<WorkResponse>
    fun getWorksByCompanyProviding(companyProviding: String): List<WorkResponse>

}
