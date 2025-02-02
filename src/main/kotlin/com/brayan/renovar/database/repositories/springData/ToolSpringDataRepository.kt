package com.brayan.renovar.database.repositories.springData

import com.brayan.renovar.database.entities.Tool
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ToolSpringDataRepository:JpaRepository<Tool, UUID>{

}
