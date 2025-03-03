package com.brayan.renovar.services.interfaces

import com.brayan.renovar.api.request.EPIUpdateRequest
import com.brayan.renovar.api.response.EpiResponse
import com.brayan.renovar.models.EPIModel
import java.util.UUID

interface EPIService {
    fun save(epi: EPIModel): EpiResponse
    fun update(epi: EPIUpdateRequest): EpiResponse
    fun listExpiredEPIs(): List<EpiResponse>
    fun listAllEPIs(): List<EpiResponse>
    fun findById(id: UUID): EpiResponse
    fun findByName(name: String): List<EpiResponse>
    fun listNotExpiredEPIs():List<EpiResponse>


}
