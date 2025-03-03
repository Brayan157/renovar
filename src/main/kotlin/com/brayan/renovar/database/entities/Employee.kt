package com.brayan.renovar.database.entities

import com.brayan.renovar.api.response.EmployeeResponse
import com.brayan.renovar.enum.EmployeeStatus
import com.brayan.renovar.models.EmployeeModel
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID


@Entity
@Table(name = "tb_funcionario")
data class Employee(
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    val id: UUID? = null,
    @Column(name = "matricula", unique = true, nullable = false)
    val registration: Int? = null,
    @Column(name = "nome")
    val name: String,
    @Column
    val cpf: String,
    @Column(name = "data_nascimento")
    val birthDate: LocalDate,
    @Column(name = "creation_date")
    @CreationTimestamp
    val creationDate: LocalDateTime? = null,
    @Column(name = "update_date")
    @UpdateTimestamp
    val updateDate: LocalDateTime? = null,
    @Column(name = "telefone")
    val phones: String,
    @OneToOne
    @JoinColumn(name = "endereco_id")
    @JsonManagedReference
    val address: Address,
    @OneToOne
    @JoinColumn(name = "funcao_id")
    @JsonManagedReference
    val function: Function,
    @OneToMany(mappedBy = "employee", cascade = [CascadeType.ALL])
    @JsonBackReference
    val employeesWorks: List<EmployeeWork> = mutableListOf(),
    @Column(name = "registro_geral")
    val generalRegistration: String,
    @Column(name = "salario_hora")
    val hourlyRate: Double,
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    val employeeStatus: EmployeeStatus,
    @OneToMany(mappedBy = "employee", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonBackReference
    val employeeEpis: List<EmployeeEPI> = mutableListOf(),
    @OneToMany(mappedBy = "employee", cascade = [CascadeType.ALL])
    @JsonBackReference
    val toolsEmployee: List<ToolsEmployees> = mutableListOf()

){
    fun toEmployeeModel() = EmployeeModel(
        id = id,
        name = name,
        cpf = cpf,
        birthDate = birthDate,
        creationDate = creationDate,
        updateDate = updateDate,
        phones = phones,
        addressModel = address.toAddressModel(),
        functionModel = function.toFunctionModel(),
        employeesWorks = employeesWorks.map { it.toEmployeeWorkModel() },
        generalRegistration = generalRegistration,
        hourlyRate = hourlyRate,
        employeeStatus = employeeStatus,
        registration = registration!!,
        employeeEpis = employeeEpis.map { it.toEmployeeEPIModel() },
        toolsEmployee = toolsEmployee.map { it.toToolsEmployeesModel() }
    )
    fun toEmployeeResponse() = EmployeeResponse(
        id = id!!,
        name = name,
        registration = registration!!,
        cpf = cpf,
        birthDate = birthDate,
        phones = phones,
        generalRegistration = generalRegistration,
        hourlyRate = hourlyRate,
        function = function.function,
        status = employeeStatus,
        addressModel = address.toAddressModel()
    )

companion object{
        fun of (employeeModel: EmployeeModel, works:List<Work>, epi:List<EPI>, tools: List<Tool>): Employee{
            val employee = Employee(
                id = employeeModel.id,
                registration = employeeModel.registration,
                name = employeeModel.name,
                cpf = employeeModel.cpf,
                birthDate = employeeModel.birthDate,
                creationDate = employeeModel.creationDate,
                updateDate = employeeModel.updateDate,
                phones = employeeModel.phones,
                address = Address.of(employeeModel.addressModel),
                function = Function.of(employeeModel.functionModel),
                generalRegistration = employeeModel.generalRegistration,
                hourlyRate = employeeModel.hourlyRate,
                employeeStatus = employeeModel.employeeStatus,
            )
            val employeesWorks = if (works.isNotEmpty() && employeeModel.employeesWorks.isNotEmpty()) {
                employeeModel.employeesWorks.mapNotNull { employeeWorkModel ->
                    val work = works.find { it.id == employeeWorkModel.workId }
                    work?.let {
                        EmployeeWork(
                            id = EmployeeWorkKey(employee.id, work.id, employeeWorkModel.creationDateId),
                            employee = employee,
                            work = work,
                            startDate = employeeWorkModel.startDate,
                            endDate = employeeWorkModel.endDate,
                            creationDateEntity = CreationDate()
                        )
                    }
                }
            } else emptyList()

            val employeeEpis = if (epi.isNotEmpty() && employeeModel.employeeEpis.isNotEmpty()) {
                employeeModel.employeeEpis.mapNotNull { employeeEPIModel ->
                    val foundEpi = epi.find { it.id == employeeEPIModel.epiId }
                    foundEpi?.let {
                        EmployeeEPI(
                            id = EmployeeEPIId(employee.id, foundEpi.id, employeeEPIModel.creationDateId),
                            employee = employee,
                            epi = foundEpi,
                            quantity = employeeEPIModel.quantity,
                            deliveryDate = employeeEPIModel.deliveryDate,
                            reason = employeeEPIModel.reason,
                            epiStatus = employeeEPIModel.epiStatus,
                            returnDate = employeeEPIModel.returnDate,
                            updateDate = employeeEPIModel.updateDate,
                            creationDate = employeeEPIModel.creationDate,
                            creationDateEntity = CreationDate()
                        )
                    }
                }
            } else emptyList()

            val employeeTools = if (tools.isNotEmpty() && employeeModel.toolsEmployee.isNotEmpty()) {
                employeeModel.toolsEmployee.mapNotNull { employeeTools ->
                    val foundTool = tools.find { it.id == employeeTools.toolId }
                    foundTool?.let {
                        ToolsEmployees(
                            id = ToolsEmployeesKey(employee.id, foundTool.id, employeeTools.creationDateId),
                            employee = employee,
                            tool = foundTool,
                            startDate = employeeTools.startDate,
                            endDate = employeeTools.endDate,
                            quantity = employeeTools.quantity,
                            status = employeeTools.status,
                            creationDate = employeeTools.creationDate,
                            updateDate = employeeTools.updateDate,
                            creationDateEntity = CreationDate()

                        )
                    }
                }
            } else emptyList()

            return employee.copy(employeesWorks = employeesWorks, employeeEpis = employeeEpis, toolsEmployee = employeeTools)
        }
    }
}

