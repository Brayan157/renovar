package com.brayan.renovar.database.repositories.implementations

import com.brayan.renovar.database.entities.Tool
import com.brayan.renovar.database.repositories.interfaces.ToolRepository
import com.brayan.renovar.database.repositories.springData.ToolSpringDataRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ToolRepositoryImpl(
    val toolJpaRepository: ToolSpringDataRepository
):ToolRepository {
    override fun findAllById(ids: List<UUID>): List<Tool> {
        return toolJpaRepository.findAllById(ids)
    }
}
