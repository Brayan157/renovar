package com.brayan.renovar.database.repositories.springData

import com.brayan.renovar.database.entities.Work
import com.brayan.renovar.enum.WorkStatus
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface WorkSpringDataRepository:JpaRepository<Work, UUID> {
    fun findByWorkStatus(status: WorkStatus): List<Work>
    fun findByCompanyProvidingContainingIgnoreCase(companyProviding: String): List<Work>

}
