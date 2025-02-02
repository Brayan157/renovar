package com.brayan.renovar.database.repositories.springData

import org.springframework.data.jpa.repository.JpaRepository
import com.brayan.renovar.database.entities.Function
import java.util.UUID

interface FunctionSpringDataRepository:JpaRepository<Function, UUID> {
    fun findByFunction(function: String): List<Function>
}