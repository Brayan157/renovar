package com.brayan.renovar.database.entities

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.UUID

@Embeddable
class ToolsWorkId(
    @Column(name = "tools_id")
    val toolsId: UUID? = null,
    @Column(name = "work_id")
    val workId: UUID? = null,
    @Column(name = "creation_date")
    val creationDateId: UUID? = null
): Serializable
