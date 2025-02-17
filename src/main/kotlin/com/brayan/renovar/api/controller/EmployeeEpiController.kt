package com.brayan.renovar.api.controller

import com.brayan.renovar.api.request.AddEpiToEmployeeRequest
import com.brayan.renovar.api.request.ReturnEpiToEmployeeRequest
import com.brayan.renovar.services.interfaces.EmployeeEpiService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/employeeEpi")

class EmployeeEpiController(
    private val employeeEpiService: EmployeeEpiService
) {
    @PostMapping
    fun addEpiToEmployee(@RequestBody addEpiToEmployeeRequest: AddEpiToEmployeeRequest) = employeeEpiService.addEpiToEmployee(addEpiToEmployeeRequest)

    @PutMapping
    fun updateEpiToEmployee(@RequestBody returnEpiToEmployeeRequest: ReturnEpiToEmployeeRequest) = employeeEpiService.ReturnEpiToEmployee(returnEpiToEmployeeRequest)

    @GetMapping
    fun getEmployeeEpi() = employeeEpiService.getEmployeeEpi()
}