package com.brayan.renovar.database.entities

import com.brayan.renovar.models.EmployeeEPIModel
import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import jakarta.persistence.Table

@Entity
@Table(name = "tb_funcionario_epi")
data class EmployeeEPI(
    @EmbeddedId
    private val id: EmployeeEPIId = EmployeeEPIId(),

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "funcionario_id")
    val employee: Employee,

    @ManyToOne
    @MapsId("epiId")
    @JoinColumn(name = "epi_id")
    val epi: EPI,
    @Column(name = "quantidade")
    val quantity: Int,
    @Column(name = "data_entrega")
    val deliveryDate: String,
    @Column(name = "motivo")
    val reason:String
){
    fun toEmployeeEPIModel() = EmployeeEPIModel(
        employeeId = employee.id!!,
        epiId = epi.id!!,
        quantity = quantity,
        deliveryDate = deliveryDate,
        reason = reason
    )

}
