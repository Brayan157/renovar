package com.brayan.renovar.services.implementations

import com.brayan.renovar.api.request.AddEmployeesWorkRequest
import com.brayan.renovar.api.request.AddToolWorkRequest
import com.brayan.renovar.api.request.RemoveEmployeesWork
import com.brayan.renovar.api.request.RemoveToolWorkRequest
import com.brayan.renovar.api.request.WorkUpdateRequest
import com.brayan.renovar.database.repositories.interfaces.EmployeeRepository
import com.brayan.renovar.database.repositories.interfaces.ToolRepository
import com.brayan.renovar.database.repositories.interfaces.WorkRepository
import com.brayan.renovar.enum.WorkStatus
import com.brayan.renovar.models.EmployeeModel
import com.brayan.renovar.models.EmployeeWorkModel
import com.brayan.renovar.models.ToolsWorkModel
import com.brayan.renovar.models.WorkModel
import com.brayan.renovar.services.interfaces.EmployeeService
import com.brayan.renovar.services.interfaces.WorkService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class WorkServiceImpl (
    val workRepository: WorkRepository,
    val employeeRepository: EmployeeRepository,
    val toolRepository: ToolRepository
): WorkService {
    override fun createWork(workModel: WorkModel): WorkModel {
        return workRepository.save(workModel)
    }

    override fun updateWork(workUpdate: WorkUpdateRequest): WorkModel {
        val work = workRepository.findById(workUpdate.id)
        val workModel = work.copy(
            companyProviding = workUpdate.companyProviding ?: work.companyProviding,
            workStatus = workUpdate.workStatus ?: work.workStatus
        )
        return workRepository.save(workModel)
    }

    override fun getWorks(): List<WorkModel> {
        return workRepository.findAll()
    }

    override fun getWorkById(workId: UUID): WorkModel {
        return workRepository.findById(workId)
    }

    override fun getWorksByStatus(status: WorkStatus): List<WorkModel> {
        return workRepository.findByWorkStatus(status)
    }

    override fun getWorksByCompanyProviding(companyProviding: String): List<WorkModel> {
        return workRepository.findByCompanyProviding(companyProviding)
    }

    override fun addEmployeeToWork(addEmployeesWorkRequest: AddEmployeesWorkRequest): WorkModel {
        val work = workRepository.findById(addEmployeesWorkRequest.workId)
        val employees = mutableListOf<EmployeeWorkModel>()
        val employeesIds = employeeRepository.findAllById(addEmployeesWorkRequest.employees)
        employees.addAll(employeesIds.map { employee ->
            EmployeeWorkModel(
                employeeId = employee.id!!,
                workId = work.id!!,
                startDate = addEmployeesWorkRequest.startDate
            )
        })
        val workModel = work.copy(
            employeesWorks = employees
        )
        return workRepository.save(workModel)
    }

    override fun removeEmployeeFromWork(removeEmployeesWork: RemoveEmployeesWork): WorkModel {
        val work = workRepository.findById(removeEmployeesWork.workId)

        val employeesToRemove = employeeRepository.findAllById(removeEmployeesWork.employees)

        val updatedEmployeesWorks = work.employeesWorks.map { employeeWork ->
            if (employeesToRemove.any { it.id == employeeWork.employeeId } &&
                employeeWork.endDate == null &&
                employeeWork.workId == work.id
            ) {
                employeeWork.copy(endDate = removeEmployeesWork.endDate) // Atualiza a data de término
            } else {
                employeeWork
            }
        }

        // Atualizando a lista de empregados do trabalho
        val updatedWork = work.copy(employeesWorks = updatedEmployeesWorks)
        return workRepository.save(updatedWork)
    }

    override fun addToolToWork(addToolWork: AddToolWorkRequest): WorkModel {
        val work = workRepository.findById(addToolWork.workId)
        val tools = toolRepository.findAllById(addToolWork.toolIds)
        val toolsWork = mutableListOf<ToolsWorkModel>()
        toolsWork.addAll(tools.map { tool ->
            ToolsWorkModel(
                toolsId = tool.id!!,
                workId = work.id!!,
                reason = addToolWork.reason,
                entryDate = addToolWork.entryDate
            )
        })
        val workModel = work.copy(
            toolsWorks = toolsWork
        )
        return workRepository.save(workModel)
    }

    override fun removeToolFromWork(removeToolWork: RemoveToolWorkRequest): WorkModel {
        val work = workRepository.findById(removeToolWork.workId)
        val toolsToRemove = toolRepository.findAllById(removeToolWork.toolIds)
        val updatedToolsWorks = work.toolsWorks.map { toolWork ->
            if (toolsToRemove.any { it.id == toolWork.toolsId } &&
                toolWork.workId == work.id
            ) {
                toolWork.copy(exitDate = removeToolWork.exitDate)
            } else {
                toolWork
            }
        }
        val updatedWork = work.copy(toolsWorks = updatedToolsWorks)
        return workRepository.save(updatedWork)
    }
}