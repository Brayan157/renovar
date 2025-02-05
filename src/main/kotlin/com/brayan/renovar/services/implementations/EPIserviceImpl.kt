package com.brayan.renovar.services.implementations

import com.brayan.renovar.api.request.EPIUpdateRequest
import com.brayan.renovar.database.repositories.interfaces.EPIRepository
import com.brayan.renovar.models.EPIModel
import com.brayan.renovar.services.interfaces.EPIService
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.UUID

@Service
class EPIserviceImpl(
    val epiRepository: EPIRepository,
):EPIService {
    override fun save(epi: EPIModel): EPIModel {
        return epiRepository.save(epi)
    }

    override fun update(epi: EPIUpdateRequest): EPIModel {
        val epiModel = epiRepository.findById(epi.id)
        val epiUpdate = epiModel.copy(
            name = epi.name ?: epiModel.name,
            approvalCertificate = epi.approvalCertificate ?: epiModel.approvalCertificate,
            quantity = epi.quantity ?: epiModel.quantity,
            unitValue = epi.unitValue ?: epiModel.unitValue,
            manufacturingDate = epi.manufacturingDate ?: epiModel.manufacturingDate,
            expirationDate = epi.expirationDate ?: epiModel.expirationDate,
            tag = epi.tag ?: epiModel.tag,
            lot = epi.lot ?: epiModel.lot
        )
        return epiRepository.save(epiUpdate)
    }

    override fun listExpiredEPIs(): List<EPIModel> {
        val currentDate = LocalDate.now()
        return epiRepository.listExpiredEPIs(currentDate)
    }

    override fun listAllEPIs(): List<EPIModel> {
        return epiRepository.findAll()
    }

    override fun findById(id: UUID): EPIModel {
        return epiRepository.findById(id)
    }

    override fun findByName(name: String): List<EPIModel> {
        return epiRepository.findByName(name)
    }

    override fun listNotExpiredEPIs(): List<EPIModel> {
        val currentDate = LocalDate.now()
        return epiRepository.listNotExpiredEPIs(currentDate)
    }

}