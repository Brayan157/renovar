package com.brayan.renovar.database.entities

import com.brayan.renovar.models.EmployeeWorkModel
import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
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
@Table(name = "tb_funcionario_obra")
data class EmployeeWork(
    @EmbeddedId
    private val id: EmployeeWorkKey = EmployeeWorkKey(),

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "funcionario_id")
    val employee: Employee,

    @ManyToOne
    @MapsId("workId")
    @JoinColumn(name = "obra_id")
    val work: Work,
    @Column(name = "data_inicio")
    val startDate: LocalDate? = null,
    @Column(name = "data_fim")
    val endDate: LocalDate? = null,
    @Column(name = "creation_date")
    @CreationTimestamp
    val creationDate: LocalDateTime? = null,
    @Column(name = "update_date")
    @UpdateTimestamp
    val updateData: LocalDateTime? = null,
) {
    fun toEmployeeWorkModel() = EmployeeWorkModel(
        employeeId = employee.id!!,
        workId = work.id!!,
        startDate = startDate,
        endDate = endDate,
        creationDate = creationDate,
        updateData = updateData
    )
    companion object{
        fun fromEmployeeWorkModel(employeeWorkModel: EmployeeWorkModel, employee: Employee, work: Work) = EmployeeWork(
            id = EmployeeWorkKey(employeeId = employeeWorkModel.employeeId, workId = employeeWorkModel.workId),
            employee = employee,
            work = work,
            startDate = employeeWorkModel.startDate,
            endDate = employeeWorkModel.endDate,
            creationDate = employeeWorkModel.creationDate,
            updateData = employeeWorkModel.updateData
        )
    }
}
