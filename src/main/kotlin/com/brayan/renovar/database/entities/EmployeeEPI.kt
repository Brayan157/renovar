package com.brayan.renovar.database.entities

import com.brayan.renovar.api.response.EmployeeEpiResponse
import com.brayan.renovar.enum.EPIStatus
import com.brayan.renovar.models.EmployeeEPIModel
import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "tb_epis_funcionario")
data class EmployeeEPI(
    @EmbeddedId
    private val id: EmployeeEPIId = EmployeeEPIId(),

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "id_funcionario")
    val employee: Employee,

    @ManyToOne
    @MapsId("epiId")
    @JoinColumn(name = "id_epi")
    val epi: EPI,

    @ManyToOne
    @MapsId("creationDateId")
    @JoinColumn(name = "id_creation_date")
    val creationDateEntity: CreationDate,

    @Column(name = "quantidade")
    val quantity: Int,
    @Column(name = "data_entrega")
    val deliveryDate: LocalDate,
    @Column(name = "motivo")
    val reason:String,
    @Column(name = "data_devolucao")
    val returnDate: LocalDate? = null,
    @Column(name = "status_epi")
    @Enumerated(EnumType.STRING)
    val epiStatus: EPIStatus,
    @Column(name = "creation_date")
    @CreationTimestamp
    val creationDate: LocalDateTime? = null,
    @Column(name = "update_date")
    @UpdateTimestamp
    val updateDate: LocalDateTime? = null
){
    fun toEmployeeEPIModel() = EmployeeEPIModel(
        employeeId = employee.id!!,
        epiId = epi.id!!,
        quantity = quantity,
        deliveryDate = deliveryDate,
        returnDate = returnDate,
        epiStatus = epiStatus,
        reason = reason,
        updateDate = updateDate,
        creationDate = creationDate,
        creationDateId = creationDateEntity.id!!
    )
    fun toEmployeeEPIResponse() = EmployeeEpiResponse(
        employee = employee.toEmployeeResponse(),
        epi = epi.toEpiResponse(),
        creationDateId = creationDateEntity.id!!,
        quantity = quantity,
        deliveryDate = deliveryDate,
        reason = reason,
        returnDate = returnDate,
        epiStatus = epiStatus
    )

}
