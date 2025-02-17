package com.brayan.renovar.database.repositories.springData

import com.brayan.renovar.database.entities.Employee
import com.brayan.renovar.enum.EmployeeStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface EmployeeSpringDataRepository: JpaRepository<Employee, UUID> {
    @Query("SELECT COALESCE(MAX(e.registration), 0) FROM Employee e")
        fun findLastRegistration(): Int
    fun findByEmployeeStatus(ativo: EmployeeStatus): List<Employee>
    fun findByNameContainingIgnoreCase(name: String): List<Employee>
    fun findByRegistration(registration: Int): Employee
    fun findByCpf(cpf: String): Employee


}
