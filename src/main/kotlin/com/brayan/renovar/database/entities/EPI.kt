package com.brayan.renovar.database.entities

import com.brayan.renovar.models.EPIModel
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.crossstore.ChangeSetPersister
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "tb_epi")
data class EPI(
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    val id: UUID? = null,
    @Column(name = "nome")
    val name: String,
    @Column(name = "certificado_aprovacao")
    val approvalCertificate: String? = null,
    @Column(name = "quantidade")
    val quantity: Int,
    @Column(name = "valor_unitario")
    val unitValue: Double? = null,
    @Column(name = "data_fabricacao")
    val manufacturingDate: LocalDate,
    @Column(name = "data_validade")
    val expirationDate: LocalDate? = null,
    @Column(name = "tag")
    val tag: String? = null,
    @Column(name = "lote")
    val lot:String? = null,
    @Column(name = "creation_date")
    @CreationTimestamp
    val creationDate: LocalDateTime? = null,
    @UpdateTimestamp
    val updateDate: LocalDateTime? = null,
    @OneToMany(mappedBy = "epi", cascade = [CascadeType.ALL], orphanRemoval = true)
    val employeeEpis: List<EmployeeEPI> = mutableListOf()
){
    fun toEPIModel() = EPIModel(
        id = id,
        name = name,
        approvalCertificate = approvalCertificate,
        quantity = quantity,
        unitValue = unitValue,
        manufacturingDate = manufacturingDate,
        expirationDate = expirationDate,
        tag = tag,
        lot = lot,
        creationDate = creationDate,
        updateDate = updateDate,
        employeeEpis = employeeEpis.map { it.toEmployeeEPIModel() }
    )
    companion object {
        fun of (epiModel:EPIModel, employees:List<Employee>):EPI {
            val epi = EPI(
                id = epiModel.id,
                name = epiModel.name,
                approvalCertificate = epiModel.approvalCertificate,
                quantity = epiModel.quantity,
                unitValue = epiModel.unitValue,
                manufacturingDate = epiModel.manufacturingDate,
                expirationDate = epiModel.expirationDate,
                tag = epiModel.tag,
                lot = epiModel.lot,
                creationDate = epiModel.creationDate,
                updateDate = epiModel.updateDate
            )
            val employeeEpis = epiModel.employeeEpis.map { employeeEPIModel ->
                val employee = employees.find { it.id == employeeEPIModel.employeeId } ?: throw ChangeSetPersister.NotFoundException()
                EmployeeEPI(
                    id = EmployeeEPIId(employee.id, epi.id),
                    employee = employee,
                    epi = epi,
                    quantity = employeeEPIModel.quantity,
                    deliveryDate = employeeEPIModel.deliveryDate,
                    reason = employeeEPIModel.reason,
                    epiStatus = employeeEPIModel.epiStatus,
                    creationDate = employeeEPIModel.creationDate,
                    updateDate = employeeEPIModel.updateDate,
                    returnDate = employeeEPIModel.returnDate
                )
            }
            return epi.copy(employeeEpis = employeeEpis)
        }
    }
}
