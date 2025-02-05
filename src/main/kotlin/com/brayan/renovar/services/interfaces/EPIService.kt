package com.brayan.renovar.services.interfaces

import com.brayan.renovar.api.request.EPIUpdateRequest
import com.brayan.renovar.models.EPIModel
import java.util.UUID

interface EPIService {
    fun save(epi: EPIModel): EPIModel
    fun update(epi: EPIUpdateRequest): EPIModel
    fun listExpiredEPIs(): List<EPIModel>
    fun listAllEPIs(): List<EPIModel>
    fun findById(id: UUID): EPIModel
    fun findByName(name: String): List<EPIModel>
    fun listNotExpiredEPIs():List<EPIModel>


}
