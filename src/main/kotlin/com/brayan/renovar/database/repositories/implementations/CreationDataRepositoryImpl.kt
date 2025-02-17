package com.brayan.renovar.database.repositories.implementations

import com.brayan.renovar.database.entities.CreationDate
import com.brayan.renovar.database.repositories.interfaces.CreationRepository
import com.brayan.renovar.database.repositories.springData.CreationSpringDataRepository
import org.springframework.stereotype.Component
import java.util.UUID
@Component
class CreationDataRepositoryImpl(
    private val creationJpaRepository: CreationSpringDataRepository
): CreationRepository {
    override fun saveCreation(): UUID? {
        return creationJpaRepository.save(CreationDate()).id
    }

    override fun findCreationById(id: UUID): UUID? {
        return creationJpaRepository.findById(id).map { it.id }.orElse(null)
    }
}