package com.brayan.renovar.api.controller

import com.brayan.renovar.api.request.AddEmployeesWorkRequest
import com.brayan.renovar.api.request.ReturnEmployeeToWork
import com.brayan.renovar.database.entities.EmployeeWorkKey
import com.brayan.renovar.services.interfaces.EmployeeWorkService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/employeeWork")
class EmployeeWorkController(
    val employeeWorkService: EmployeeWorkService
) {
    @PostMapping
    fun saveEmployeeWork(@RequestBody addEmployeesWorkRequest: AddEmployeesWorkRequest) = employeeWorkService.saveEmployeeWork(addEmployeesWorkRequest)

    @PutMapping
    fun updateEmployeeWork(@RequestBody returnEmployeeToWork: ReturnEmployeeToWork) = employeeWorkService.updateEmployeeWork(returnEmployeeToWork)
    @GetMapping
    fun getEmployeeWork() = employeeWorkService.getEmployeeWork()
    @GetMapping("/id")
    fun getEmployeeWorkById(@RequestBody id: EmployeeWorkKey) = employeeWorkService.getEmployeeWorkById(id)
    //listar somente os funcionários que estão trabalhando
    @GetMapping("/working")
    fun getEmployeeWorkWorking() = employeeWorkService.getEmployeeWorkWorking()
    //listar somente os funcionários que não estão trabalhando
    @GetMapping("/notWorking")
    fun getEmployeeWorkNotWorking() = employeeWorkService.getEmployeeWorkNotWorking()

    //listar obra que o funcionário está trabalhando
    @GetMapping("/work/{employeeId}")
    fun getEmployeeWorkByEmployeeId(@PathVariable employeeId: UUID) = employeeWorkService.getEmployeeWorkByEmployeeId(employeeId)
    //listar todos os funcionários que estão trabalhando em uma obra
    @GetMapping("/workId/{workId}")
    fun getEmployeeWorkByWorkId(@PathVariable workId: UUID) = employeeWorkService.getEmployeeWorkByWorkId(workId)
}