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
        fun of(workModel: WorkModel, employees: List<Employee>, tools: List<Tool>): Work{
            val work = Work(
                id = workModel.id,
                companyProviding = workModel.companyProviding,
                cnpj = workModel.cnpj,
                workStatus = workModel.workStatus,
                creationDate = workModel.creationDate,
                updateDate = workModel.updateDate,
                address = Address.of(workModel.address),
            )
            val employeesWorks = if (employees.isNotEmpty() && workModel.employeesWorks.isNotEmpty()) {
                workModel.employeesWorks.mapNotNull { employeeWorkModel ->
                    val employee = employees.find { it.id == employeeWorkModel.employeeId }
                    employee?.let {
                        EmployeeWork(
                            id = EmployeeWorkKey(employee.id, work.id),
                            employee = employee,
                            work = work,
                            startDate = employeeWorkModel.startDate,
                            endDate = employeeWorkModel.endDate
                        )
                    }
                }
            } else emptyList()
            val toolsWorks = if (tools.isNotEmpty() && workModel.toolsWorks.isNotEmpty()) {
                workModel.toolsWorks.mapNotNull { toolsWorkModel ->
                    val tool = tools.find { it.id == toolsWorkModel.toolsId }
                    tool?.let {
                        ToolsWork(
                            id = ToolsWorkId(tool.id, work.id),
                            tool = tool,
                            work = work,
                            reason = toolsWorkModel.reason,
                            entryDate = toolsWorkModel.entryDate,
                            exitDate = toolsWorkModel.exitDate,
                            creationDate = toolsWorkModel.creationDate,
                            updateDate = toolsWorkModel.updateDate
                        )
                    }
                }
            } else emptyList()

            return work.copy(employeesWorks = employeesWorks, toolsWorks = toolsWorks)
        }


    }
}
