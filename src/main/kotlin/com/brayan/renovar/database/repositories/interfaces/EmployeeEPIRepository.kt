package com.brayan.renovar.database.repositories.interfaces

import com.brayan.renovar.database.entities.EmployeeEPI
import com.brayan.renovar.models.EmployeeEPIModel
import org.springframework.stereotype.Component
import java.util.UUID

interface EmployeeEPIRepository {
    fun save(employeeEpiModel: EmployeeEPIModel): EmployeeEPI

}
