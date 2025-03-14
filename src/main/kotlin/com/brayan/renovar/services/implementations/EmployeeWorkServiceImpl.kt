package com.brayan.renovar.services.implementations

import com.brayan.renovar.api.request.AddEmployeesWorkRequest
import com.brayan.renovar.api.request.ReturnEmployeeToWork
import com.brayan.renovar.api.response.EmployeeWorkResponse
import com.brayan.renovar.database.entities.EmployeeWorkKey
import com.brayan.renovar.database.repositories.interfaces.CreationRepository
import com.brayan.renovar.database.repositories.interfaces.EmployeeRepository
import com.brayan.renovar.database.repositories.interfaces.EmployeeWorkRespository
import com.brayan.renovar.database.repositories.interfaces.WorkRepository
import com.brayan.renovar.enum.EmployeeStatus
import com.brayan.renovar.enum.WorkStatus
import com.brayan.renovar.models.EmployeeWorkModel
import com.brayan.renovar.services.interfaces.EmployeeWorkService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class EmployeeWorkServiceImpl(
    val employeeWorkRepository: EmployeeWorkRespository,
    val creationRepository: CreationRepository,
    val workRepository: WorkRepository,
    val employeeRepository: EmployeeRepository
): EmployeeWorkService {
    override fun saveEmployeeWork(addEmployeesWorkRequest: AddEmployeesWorkRequest): EmployeeWorkResponse {
        // verificar se o funcionario não está trabalhando em outra obra
        // todas as obras que o funcionário estiver vinculado e endDate precisa ser difernte de nulo
        if (employeeWorkRepository.findByEmployeeIdAndEndDateIsNull(addEmployeesWorkRequest.employeeId).isNotEmpty()) {
            throw Exception("Funcionário já está trabalhando em outra obra")
        }
        val work = workRepository.findById(addEmployeesWorkRequest.workId)
        if (work.workStatus == WorkStatus.FINALIZADA){
            throw Exception("Obra finalizada")
        }
        val employee = employeeRepository.findEmployeeById(addEmployeesWorkRequest.employeeId)
        if (employee.status == EmployeeStatus.INATIVO || employee.status == EmployeeStatus.DEMITIDO || employee.status == EmployeeStatus.DESLIGADO){
            throw Exception("Funcionário não está trabalhando")
        }
        val employeeWork = EmployeeWorkModel(
            employeeId = addEmployeesWorkRequest.employeeId,
            workId = addEmployeesWorkRequest.workId,
            startDate = addEmployeesWorkRequest.startDate,
            creationDateId = creationRepository.saveCreation() ?: throw Exception("Erro ao salvar data de criação")
        )

        return employeeWorkRepository.save(employeeWork).toEmployeeWorkResponse(work.toResponse(), employee, creationRepository.findCreationById(employeeWork.creationDateId))
    }

    override fun updateEmployeeWork(returnEmployeeToWork: ReturnEmployeeToWork): EmployeeWorkResponse {
        val employeeWork = employeeWorkRepository.findById(returnEmployeeToWork.id)
        val employeeWorkUpdate = employeeWork.copy(
            endDate = returnEmployeeToWork.returnDate
        )
        return employeeWorkRepository.save(employeeWorkUpdate).toEmployeeWorkResponse(
            work = workRepository.findById(employeeWork.workId).toResponse(),
            employee = employeeRepository.findEmployeeById(employeeWork.employeeId),
            creationDate = creationRepository.findCreationById(employeeWork.creationDateId)
        )
    }

    override fun getEmployeeWork(): List<EmployeeWorkResponse> {
        return employeeWorkRepository.findAll().map { it.toEmployeeWorkResponse(
            work = workRepository.findById(it.workId).toResponse(),
            employee = employeeRepository.findEmployeeById(it.employeeId),
            creationDate = creationRepository.findCreationById(it.creationDateId)
        ) }
    }

    override fun getEmployeeWorkById(id: EmployeeWorkKey): EmployeeWorkResponse {
        return employeeWorkRepository.findById(id).toEmployeeWorkResponse(
            work = workRepository.findById(id.workId!!).toResponse(),
            employee = employeeRepository.findEmployeeById(id.employeeId!!),
            creationDate = creationRepository.findCreationById(id.creationDateId!!)
        )
    }

    override fun getEmployeeWorkWorking(): List<EmployeeWorkResponse> {
        return employeeWorkRepository.findByEndDateIsNull().map { it.toEmployeeWorkResponse(
            work = workRepository.findById(it.workId).toResponse(),
            employee = employeeRepository.findEmployeeById(it.employeeId),
            creationDate = creationRepository.findCreationById(it.creationDateId)
        ) }
    }

    override fun getEmployeeWorkNotWorking(): List<EmployeeWorkResponse> {
        return employeeWorkRepository.findByEndDateIsNotNull().map { it.toEmployeeWorkResponse(
            work = workRepository.findById(it.workId).toResponse(),
            employee = employeeRepository.findEmployeeById(it.employeeId),
            creationDate = creationRepository.findCreationById(it.creationDateId)
        ) }
    }

    override fun getEmployeeWorkByEmployeeId(employeeId: UUID): List<EmployeeWorkResponse> {
        // listar obra que o funcionário está trabalhando e que o endDate é nulo
        return employeeWorkRepository.findByEmployeeIdAndEndDateIsNull(employeeId).map { it.toEmployeeWorkResponse(
            work = workRepository.findById(it.workId).toResponse(),
            employee = employeeRepository.findEmployeeById(it.employeeId),
            creationDate = creationRepository.findCreationById(it.creationDateId)
        ) }
    }

    override fun getEmployeeWorkByWorkId(workId: UUID): List<EmployeeWorkResponse> {
        return employeeWorkRepository.findByWorkId(workId).map { it.toEmployeeWorkResponse(
            work = workRepository.findById(it.workId).toResponse(),
            employee = employeeRepository.findEmployeeById(it.employeeId),
            creationDate = creationRepository.findCreationById(it.creationDateId)
        ) }
    }

}