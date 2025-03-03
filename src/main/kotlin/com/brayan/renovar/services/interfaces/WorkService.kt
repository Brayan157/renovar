package com.brayan.renovar.services.interfaces

import com.brayan.renovar.api.request.AddEmployeesWorkRequest
import com.brayan.renovar.api.request.WorkUpdateRequest
import com.brayan.renovar.enum.WorkStatus
import com.brayan.renovar.models.WorkModel
import java.util.UUID

interface WorkService {
    fun createWork(workModel: WorkModel): WorkModel
    fun updateWork(workUpdate: WorkUpdateRequest): WorkModel
    fun getWorks(): List<WorkModel>
    fun getWorkById(workId: UUID): WorkModel
    fun getWorksByStatus(status: WorkStatus): List<WorkModel>
    fun getWorksByCompanyProviding(companyProviding: String): List<WorkModel>

}
