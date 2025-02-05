package com.brayan.renovar.database.repositories.springData

import com.brayan.renovar.database.entities.EPI
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.UUID

interface EPISpringDataRepository:JpaRepository<EPI, UUID> {
    fun findByExpirationDateBefore(date: LocalDate): List<EPI>
    fun findByNameContainingIgnoreCase(name: String): List<EPI>
    fun findByExpirationDateAfter(currentDate: LocalDate?): List<EPI>

}
