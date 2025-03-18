package com.brayan.renovar.api.controller

import com.brayan.renovar.api.request.EmployeeToolRequest
import com.brayan.renovar.api.request.EmployeeToolUpdateRequest
import com.brayan.renovar.services.interfaces.EmployeeToolService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
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
    @Operation(description = "Adicionar uma ferramenta a um funcionário")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Ferramenta adicionada com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao adicionar ferramenta"),
        ApiResponse(responseCode = "404", description = "Funcionário ou ferramenta não encontrado")
    )
    @PostMapping
    fun save(@RequestBody employeeToolRequest: EmployeeToolRequest) = employeeToolService.save(employeeToolRequest)
    @Operation(description = "Devolver uma ferramenta de um funcionário")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Ferramenta devolvida com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao devolver ferramenta"),
        ApiResponse(responseCode = "404", description = "Funcionário ou ferramenta não encontrado"),
        ApiResponse(responseCode = "409", description = "Ferramenta já devolvida")
    )
    @PutMapping
    fun update(@RequestBody employeeToolUpdateRequest: EmployeeToolUpdateRequest) = employeeToolService.update(employeeToolUpdateRequest)

    // listar todas as ferramentas emprestadas
    @Operation(description = "Listar todas as ferramentas emprestadas")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Ferramentas encontradas com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar ferramentas"),
        ApiResponse(responseCode = "404", description = "Ferramentas não encontradas")
    )
    @GetMapping
    fun listAll() = employeeToolService.listAll()
    // listar todas as ferramentas emprestadas por um funcionário
    @Operation(description = "Listar todas as ferramentas emprestadas por um funcionário")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Ferramentas encontradas com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar ferramentas"),
        ApiResponse(responseCode = "404", description = "Ferramentas não encontradas")
    )
    @GetMapping("/employee/{employeeId}")
    fun listByEmployeeId(@PathVariable employeeId: UUID) = employeeToolService.listByEmployeeId(employeeId)

    // listar todas as ferramentas emprestadas por um funcionário com status emprestado
    @Operation(description = "Listar todas as ferramentas emprestadas por um funcionário com status emprestado")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Ferramentas encontradas com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar ferramentas"),
        ApiResponse(responseCode = "404", description = "Ferramentas não encontradas")
    )
    @GetMapping("/employee/{employeeId}/status/loaned")
    fun listByEmployeeIdAndStatusLoaned(@PathVariable employeeId: UUID) = employeeToolService.listByEmployeeIdAndStatusLoaned(employeeId)
    // listar todas as ferramentas emprestadas por um funcionário com status devolvido
    @Operation(description = "Listar todas as ferramentas emprestadas por um funcionário com status devolvido")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Ferramentas encontradas com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar ferramentas"),
        ApiResponse(responseCode = "404", description = "Ferramentas não encontradas")
    )
    @GetMapping("/employee/{employeeId}/status/returned")
    fun listByEmployeeIdAndStatusReturned(@PathVariable employeeId: UUID) = employeeToolService.listByEmployeeIdAndStatusReturned(employeeId)

}