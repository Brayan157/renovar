package com.brayan.renovar.api.controller

import com.brayan.renovar.api.request.AddEpiToEmployeeRequest
import com.brayan.renovar.api.request.ReturnAllEpi
import com.brayan.renovar.api.request.ReturnEpiToEmployeeRequest
import com.brayan.renovar.api.response.EmployeeEpiResponse
import com.brayan.renovar.database.entities.EmployeeEPIId
import com.brayan.renovar.services.interfaces.EmployeeEpiService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/employeeEpi")

class EmployeeEpiController(
    private val employeeEpiService: EmployeeEpiService
) {
    @PostMapping
    fun addEpiToEmployee(@RequestBody addEpiToEmployeeRequest: AddEpiToEmployeeRequest) = employeeEpiService.addEpiToEmployee(addEpiToEmployeeRequest)

    @PutMapping
    fun updateEpiToEmployee(@RequestBody returnEpiToEmployeeRequest: ReturnEpiToEmployeeRequest) = employeeEpiService.returnEpiToEmployee(returnEpiToEmployeeRequest)

    @GetMapping("/all")
    fun getEmployeeEpi() = employeeEpiService.getEmployeeEpi()
    @GetMapping("/id")
    fun getEmployeeEpiById(@RequestBody id: EmployeeEPIId) = employeeEpiService.getEmployeeEpiId(id)
    //listar somente os epis com status entregue
    @GetMapping("/delivered")
    fun getEmployeeEpiDelivered() = employeeEpiService.getEmployeeEpiDelivered()

    //listar somente os epis com status devolvido
    @GetMapping("/returned")
    fun getEmployeeEpiReturned() = employeeEpiService.getEmployeeEpiReturned()

    //devolver todos os epis de um funcionário
    @PutMapping("/return")
    fun returnAllEpiToEmployee(@RequestBody returnAllEpi: ReturnAllEpi) = employeeEpiService.returnAllEpiToEmployee(returnAllEpi)
    //listar todos os epis de um funcionário
    @GetMapping("/employee/{employeeId}")
    fun getEmployeeEpiByEmployeeId(@PathVariable employeeId: UUID) = employeeEpiService.getEmployeeEpiByEmployeeId(employeeId)
    //listar todos os funcionarios com um epi
    @GetMapping("/epi/{epiId}")
    fun getEmployeeEpiByEpiId(@PathVariable epiId: UUID) = employeeEpiService.getEmployeeEpiByEpiId(epiId)
    //listar todos os epis que o status esteja entregue para um funcionário
    @GetMapping("/employeeDelivered/{employeeId}")
    fun getEmployeeEpiDeliveredByEmployeeId(@PathVariable employeeId: UUID) = employeeEpiService.getEmployeeEpiDeliveredByEmployeeId(employeeId)
    @GetMapping("/model")
    fun getEmployeeEpiModel() = employeeEpiService.getEmployeeEpiModel()
}