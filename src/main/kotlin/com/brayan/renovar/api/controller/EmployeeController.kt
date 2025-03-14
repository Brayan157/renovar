package com.brayan.renovar.api.controller

import com.brayan.renovar.api.request.EmployeeUpdateRequest
import com.brayan.renovar.api.request.FunctionRequest
import com.brayan.renovar.enum.EmployeeStatus
import com.brayan.renovar.models.EmployeeModel
import com.brayan.renovar.services.interfaces.EmployeeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/employee")
class EmployeeController(
    private val employeeService: EmployeeService
) {
    @PostMapping("/function")
    fun createFunction(@RequestBody functionRequest: FunctionRequest) = employeeService.createFunction(functionRequest)
    @GetMapping("/function/id/{id}")
    fun getFunctionById(@PathVariable id: UUID) = employeeService.getFunctionById(id)
    @GetMapping("/function/name/{name}")
    fun getFunctionByName(@PathVariable name: String) = employeeService.getFunctionByName(name)
    @GetMapping("/function")
    fun getFunctions() = employeeService.getFunctions()
    @PostMapping
    fun createEmployee(@RequestBody employeeModel: EmployeeModel) = employeeService.createEmployee(employeeModel)
    @GetMapping
    fun getEmployees() = employeeService.getEmployees()
    @GetMapping("/{employeeId}")
    fun getEmployeeById(@PathVariable employeeId: UUID) = employeeService.getEmployeeById(employeeId)
    @PutMapping
    fun updateEmployee(@RequestBody employeeRequest: EmployeeUpdateRequest) = employeeService.updateEmployee(employeeRequest)
    @GetMapping("/status/{status}")
    fun getEmployeesByStatus(@PathVariable status: EmployeeStatus) = employeeService.getEmployeesByStatus(status)
    @GetMapping("/name/{name}")
    fun getEmployeesByName(@PathVariable name:String) = employeeService.getEmployeesByName(name)
}
