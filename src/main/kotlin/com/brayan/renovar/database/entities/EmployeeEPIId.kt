package com.brayan.renovar.database.entities

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.UUID

@Embeddable
class EmployeeEPIId (
    @Column(name = "funcionario_id")
    val employeeId: UUID? = null,
    @Column(name = "epi_id")
    val epiId: UUID? = null
): Serializable