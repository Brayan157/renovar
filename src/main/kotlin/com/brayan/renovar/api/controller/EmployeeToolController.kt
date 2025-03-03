package com.brayan.renovar.api.controller

import com.brayan.renovar.api.request.EmployeeToolRequest
import com.brayan.renovar.api.request.EmployeeToolUpdateRequest
import com.brayan.renovar.services.interfaces.EmployeeToolService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/employeeTool")
class EmployeeToolController(
    val employeeToolService: EmployeeToolService
) {
    @PostMapping
    fun save(@RequestBody employeeToolRequest: EmployeeToolRequest) = employeeToolService.save(employeeToolRequest)
    @PutMapping
    fun update(@RequestBody employeeToolUpdateRequest: EmployeeToolUpdateRequest) = employeeToolService.update(employeeToolUpdateRequest)

    // listar todas as ferramentas emprestadas
    @GetMapping
    fun listAll() = employeeToolService.listAll()
    // listar todas as ferramentas emprestadas por um funcionário
    @GetMapping("/employee/{employeeId}")
    fun listByEmployeeId(@PathVariable employeeId: UUID) = employeeToolService.listByEmployeeId(employeeId)

    // listar todas as ferramentas emprestadas por um funcionário com status emprestado
    @GetMapping("/employee/{employeeId}/status/loaned")
    fun listByEmployeeIdAndStatusLoaned(@PathVariable employeeId: UUID) = employeeToolService.listByEmployeeIdAndStatusLoaned(employeeId)
    // listar todas as ferramentas emprestadas por um funcionário com status devolvido
    @GetMapping("/employee/{employeeId}/status/returned")
    fun listByEmployeeIdAndStatusReturned(@PathVariable employeeId: UUID) = employeeToolService.listByEmployeeIdAndStatusReturned(employeeId)

}