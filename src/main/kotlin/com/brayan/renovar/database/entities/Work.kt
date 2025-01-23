package com.brayan.renovar.database.entities
import com.brayan.renovar.enum.WorkStatus
import com.brayan.renovar.models.WorkModel
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.crossstore.ChangeSetPersister
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "tb_obra")
data class Work(
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    val id: UUID? = null,
    @Column(name = "empresa_prestante")
    val companyProviding:String,
    @Column(name = "cnpj")
    val cnpj:String,
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    val workStatus: WorkStatus,
    @Column(name = "creation_date")
    @CreationTimestamp
    val creationDate: LocalDateTime? = null,
    @Column(name = "update_date")
    @UpdateTimestamp
    val updateDate: LocalDateTime? = null,
    @OneToOne
    @JoinColumn(name = "endereco_id")
    @JsonManagedReference
    val address: Address,
    @OneToMany(mappedBy = "work", cascade = [CascadeType.ALL], orphanRemoval = true)
    val employeesWorks: List<EmployeeWork> = mutableListOf(),
    @OneToMany(mappedBy = "work", cascade = [CascadeType.ALL], orphanRemoval = true)
    val toolsWorks: List<ToolsWork> = mutableListOf()
){
    fun toWorkModel() = WorkModel(
        id = id,
        companyProviding = companyProviding,
        cnpj = cnpj,
        workStatus = workStatus,
        creationDate = creationDate,
        updateDate = updateDate,
        address = address.toAddressModel(),
        employeesWorks = employeesWorks.map { it.toEmployeeWorkModel() },
        toolsWorks = toolsWorks.map { it.toToolsWorkModel() }
    )
    companion object{
        fun of(workModel: WorkModel, employees: Map<UUID, Employee>, tools: Map<UUID, Tool>): Work{
            val work = Work(
                id = workModel.id,
                companyProviding = workModel.companyProviding,
                cnpj = workModel.cnpj,
                workStatus = workModel.workStatus,
                creationDate = workModel.creationDate,
                updateDate = workModel.updateDate,
                address = Address.of(workModel.address),
            )
            val employeesWorks = workModel.employeesWorks.map { employeesWork ->
                val employee = employees[employeesWork.employeeId] ?: throw ChangeSetPersister.NotFoundException()
                EmployeeWork(
                    id = EmployeeWorkKey(work.id, employee.id),
                    employee = employee,
                    work = work,
                    startDate = employeesWork.startDate,
                    endDate = employeesWork.endDate
                )
            }
            val toolsWorks = workModel.toolsWorks.map { toolsWorkModel ->
                val tool = tools[toolsWorkModel.toolsId] ?: throw ChangeSetPersister.NotFoundException()
                ToolsWork(
                    id = ToolsWorkId(
                        toolsId = tool.id!!,
                        workId = work.id!!
                    ),
                    tool = tool,
                    work = work,
                    reason = toolsWorkModel.reason,
                    entryDate = toolsWorkModel.entryDate,
                    exitDate = toolsWorkModel.exitDate,
                    creationDate = toolsWorkModel.creationDate,
                    updateDate = toolsWorkModel.updateDate
                )
            }

            return work.copy(employeesWorks = employeesWorks, toolsWorks = toolsWorks)
        }


    }
}
