package com.brayan.renovar.database.repositories.implementations

import com.brayan.renovar.api.response.EmployeeWorkResponse
import com.brayan.renovar.database.entities.EmployeeWork
import com.brayan.renovar.database.entities.EmployeeWorkKey
import com.brayan.renovar.database.repositories.interfaces.EmployeeWorkRespository
import com.brayan.renovar.database.repositories.springData.CreationSpringDataRepository
import com.brayan.renovar.database.repositories.springData.EmployeeSpringDataRepository
import com.brayan.renovar.database.repositories.springData.EmployeeWorkSpringDataRepository
import com.brayan.renovar.database.repositories.springData.WorkSpringDataRepository
import com.brayan.renovar.models.EmployeeWorkModel
import org.springframework.stereotype.Component
import java.util.UUID
@Component
class EmployeeWorkResponseImpl(
    private val employeeWorkJpaRepository: EmployeeWorkSpringDataRepository,
    private val employeeSpringDataRepository: EmployeeSpringDataRepository,
    private val creationJpaRepository: CreationSpringDataRepository,
    private val workJpaRepository: WorkSpringDataRepository
): EmployeeWorkRespository {
    override fun save(employeeWork: EmployeeWorkModel): EmployeeWorkModel{
        val employeeWorkSave = EmployeeWork(
            id = EmployeeWorkKey(
                employeeId = employeeWork.employeeId,
                workId = employeeWork.workId,
                creationDateId = employeeWork.creationDateId
            ),
            employee = employeeSpringDataRepository.findById(employeeWork.employeeId).get(),
            work = workJpaRepository.findById(employeeWork.workId).get(),
            startDate = employeeWork.startDate,
            creationDateEntity = creationJpaRepository.findById(employeeWork.creationDateId).get(),
            creationDate = employeeWork.creationDate
        )

        return employeeWorkJpaRepository.save(employeeWorkSave).toEmployeeWorkModel()
    }


    override fun findById(id: EmployeeWorkKey): EmployeeWorkModel {
        return employeeWorkJpaRepository.findById(id).get().toEmployeeWorkModel()
    }

    override fun findAll(): List<EmployeeWorkModel> {
        return employeeWorkJpaRepository.findAll().map { it.toEmployeeWorkModel() }
    }

    override fun findByEndDateIsNull(): List<EmployeeWorkModel> {
        return employeeWorkJpaRepository.findByEndDateIsNull().map { it.toEmployeeWorkModel() }
    }

    override fun findByEndDateIsNotNull(): List<EmployeeWorkModel> {
        return employeeWorkJpaRepository.findByEndDateIsNotNull().map { it.toEmployeeWorkModel() }
    }


    override fun findByEmployeeIdAndEndDateIsNull(employeeId: UUID): List<EmployeeWorkModel> {
        val employee = employeeSpringDataRepository.findById(employeeId).get()
        return employeeWorkJpaRepository.findByEmployeeAndEndDateIsNull(employee).map { it.toEmployeeWorkModel() }
    }

    override fun findByWorkId(workId: UUID): List<EmployeeWorkModel> {
        val work = workJpaRepository.findById(workId).get()
        return employeeWorkJpaRepository.findByWork(work).map { it.toEmployeeWorkModel() }
    }
}