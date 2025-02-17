package com.brayan.renovar.database.repositories.springData

import com.brayan.renovar.database.entities.EmployeeEPI
import com.brayan.renovar.database.entities.EmployeeEPIId
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeEpiSpringDataRepository:JpaRepository<EmployeeEPI, EmployeeEPIId> {
}