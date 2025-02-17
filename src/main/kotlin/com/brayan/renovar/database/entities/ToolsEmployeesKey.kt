package com.brayan.renovar.database.entities

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.UUID

@Embeddable
class ToolsEmployeesKey (
    @Column(name = "funcionario_id")
    val employeeId: UUID? = null,
    @Column(name = "ferramenta_id")
    val toolId: UUID? = null,
    @Column(name = "creation_date")
    val creationDateId: UUID? = null
): Serializable
