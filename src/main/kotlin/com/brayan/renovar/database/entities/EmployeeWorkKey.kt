package com.brayan.renovar.database.entities

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.UUID

@Embeddable
class EmployeeWorkKey (
    @Column(name = "funcionario_id")
    val employeeId: UUID? = null,
    @Column(name = "obra_id")
    val workId: UUID? = null,
    @Column(name = "creation_date_id")
    val creationDateId: UUID? = null
): Serializable



