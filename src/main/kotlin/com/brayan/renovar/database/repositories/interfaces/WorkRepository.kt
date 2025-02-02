package com.brayan.renovar.database.repositories.interfaces

import com.brayan.renovar.database.entities.Work
import com.brayan.renovar.enum.WorkStatus
import com.brayan.renovar.models.WorkModel
import java.util.UUID

interface WorkRepository {
    fun save(work: WorkModel): WorkModel
    fun findAll(): List<WorkModel>
    fun findById(id: UUID): WorkModel
    fun findByStatus(status: WorkStatus): List<WorkModel>
    fun findAllById(worksIds: List<UUID>): List<Work>
}
