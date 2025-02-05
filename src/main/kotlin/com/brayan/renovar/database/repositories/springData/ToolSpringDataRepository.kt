package com.brayan.renovar.database.repositories.springData

import com.brayan.renovar.database.entities.Tool
import com.brayan.renovar.enum.ToolStatus
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ToolSpringDataRepository:JpaRepository<Tool, UUID>{
     fun findByNameContainingIgnoreCase(name: String): List<Tool>
     fun findByToolStatus(status: ToolStatus): List<Tool>
     fun findByToolStatusNot(deletada: ToolStatus): List<Tool>

}
