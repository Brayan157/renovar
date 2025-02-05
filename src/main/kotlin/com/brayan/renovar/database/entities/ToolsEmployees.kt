package com.brayan.renovar.database.entities

import com.brayan.renovar.enum.ToolEmployee
import com.brayan.renovar.models.ToolsEmployeesModel
import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "tb_funcionario_ferramentas")
data class ToolsEmployees(
    @EmbeddedId
    private val id: ToolsEmployeesKey = ToolsEmployeesKey(),

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "funcionario_id")
    val employee: Employee,

    @ManyToOne
    @MapsId("toolId")
    @JoinColumn(name = "ferramenta_id")
    val tool: Tool,
    @Column(name = "data_inicio")
    val startDate: LocalDate,
    @Column(name = "data_fim")
    val endDate: LocalDate? = null,
    @Column(name = "quantidade")
    val quantity: Int? = null,
    @Column(name = "creation_date")
    @CreationTimestamp
    val creationDate: LocalDateTime? = null,
    @Column(name = "update_date")
    @UpdateTimestamp
    val updateDate: LocalDateTime? = null,
    @Column(name = "status_ferramenta")
    @Enumerated(EnumType.STRING)
    val status: ToolEmployee
){
    fun toToolsEmployeesModel() = ToolsEmployeesModel(
        employeeId = employee.id!!,
        toolId = tool.id!!,
        startDate = startDate,
        endDate = endDate,
        quantity = quantity,
        creationDate = creationDate,
        updateDate = updateDate,
        status = status
    )
}
