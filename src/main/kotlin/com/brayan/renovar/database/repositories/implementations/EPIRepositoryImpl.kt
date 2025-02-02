package com.brayan.renovar.database.repositories.implementations

import com.brayan.renovar.database.entities.EPI
import com.brayan.renovar.database.repositories.interfaces.EPIRepository
import com.brayan.renovar.database.repositories.springData.EPIJpaRepository
import org.springframework.stereotype.Component
import java.util.UUID
@Component

class EPIRepositoryImpl(
    val epiJpaRepository: EPIJpaRepository
):EPIRepository {
    override fun findAllById(ids: List<UUID>): List<EPI> {
        return epiJpaRepository.findAllById(ids)
    }
}
