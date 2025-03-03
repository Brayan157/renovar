package com.brayan.renovar.database.entities

import com.brayan.renovar.api.response.EmployeeToolResponse
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
@Table(name = "tb_ferramenta_funcionario")
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

    @ManyToOne
    @MapsId("creationDateId")
    @JoinColumn(name = "creation_date_id")
    val creationDateEntity: CreationDate,

    @Column(name = "data_entrega")
    val startDate: LocalDate,
    @Column(name = "data_devolucao")
    val endDate: LocalDate? = null,
    @Column(name = "quantidade")
    val quantity: Int,
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
        status = status,
        creationDateId = creationDateEntity.id!!
    )

    fun toResponse() = EmployeeToolResponse(
        employee = employee.toEmployeeResponse(),
        tool = tool.toResponse(),
        startDate = startDate,
        endDate = endDate,
        quantity = quantity,
        status = status,
        creationDate = creationDate,
        updateDate = updateDate,
        creationDateId = creationDateEntity.id!!
    )
}
