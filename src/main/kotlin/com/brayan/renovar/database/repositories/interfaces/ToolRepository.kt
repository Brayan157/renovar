package com.brayan.renovar.database.repositories.interfaces

import com.brayan.renovar.database.entities.Tool
import java.util.UUID

interface ToolRepository {
    fun findAllById(ids: List<UUID>): List<Tool>
}
