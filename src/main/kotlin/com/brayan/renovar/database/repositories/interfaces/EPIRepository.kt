package com.brayan.renovar.database.repositories.interfaces

import com.brayan.renovar.database.entities.EPI
import com.brayan.renovar.database.entities.Employee
import com.brayan.renovar.models.EPIModel
import java.time.LocalDate
import java.util.UUID

interface EPIRepository {
    fun findAllById (ids: List<UUID>): List<EPI>
    fun save(epi: EPIModel): EPIModel
    fun findById(id: UUID): EPIModel
    fun listExpiredEPIs(date:LocalDate): List<EPIModel>
    fun findAll(): List<EPIModel>
    fun findByName(name: String): List<EPIModel>
    fun listNotExpiredEPIs(currentDate: LocalDate?): List<EPIModel>


}
