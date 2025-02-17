package com.brayan.renovar.database.entities

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.UUID

@Embeddable
class EmployeeEPIId (
    @Column(name = "id_funcionario")
    val employeeId: UUID? = null,
    @Column(name = "id_epi")
    val epiId: UUID? = null,
    @Column(name = "creation_date")
    val creationDateId: UUID? = null
): Serializable