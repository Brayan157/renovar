package com.brayan.renovar.api.controller

import com.brayan.renovar.api.request.AddEpiToEmployeeRequest
import com.brayan.renovar.api.request.ReturnAllEpi
import com.brayan.renovar.api.request.ReturnEpiToEmployeeRequest
import com.brayan.renovar.api.response.EmployeeEpiResponse
import com.brayan.renovar.database.entities.EmployeeEPIId
import com.brayan.renovar.services.interfaces.EmployeeEpiService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
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
    @Operation(description = "Adicionar um epi a um funcionário")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Epi adicionado com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao adicionar epi"),
        ApiResponse(responseCode = "404", description = "Funcionário ou epi não encontrado")
    )
    @PostMapping
    fun addEpiToEmployee(@RequestBody addEpiToEmployeeRequest: AddEpiToEmployeeRequest) = employeeEpiService.addEpiToEmployee(addEpiToEmployeeRequest)
    @Operation(description = "Devolver um epi de um funcionário")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Epi devolvido com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao devolver epi"),
        ApiResponse(responseCode = "404", description = "Funcionário ou epi não encontrado"),
        ApiResponse(responseCode = "409", description = "Epi já devolvido")
    )
    @PutMapping
    fun updateEpiToEmployee(@RequestBody returnEpiToEmployeeRequest: ReturnEpiToEmployeeRequest) = employeeEpiService.returnEpiToEmployee(returnEpiToEmployeeRequest)
    @Operation(description = "Listar todos os epis")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Epis encontrados com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar epis"),
        ApiResponse(responseCode = "404", description = "Epis não encontrados")
    )
    @GetMapping("/all")
    fun getEmployeeEpi() = employeeEpiService.getEmployeeEpi()
    @Operation(description = "Buscar um epi de um funcionário")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Epi encontrado com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar epi"),
        ApiResponse(responseCode = "404", description = "Epi ou funcionario não encontrado")
    )
    @GetMapping("/id")
    fun getEmployeeEpiById(@RequestBody id: EmployeeEPIId) = employeeEpiService.getEmployeeEpiId(id)
    //listar somente os epis com status entregue
    @Operation(description = "Listar todos os epis com status entregue")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Epis encontrados com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar epis"),
        ApiResponse(responseCode = "404", description = "Epis ou funcionario não encontrados"),
        ApiResponse(responseCode = "409", description = "Epis não entregues")
    )
    @GetMapping("/delivered")
    fun getEmployeeEpiDelivered() = employeeEpiService.getEmployeeEpiDelivered()

    //listar somente os epis com status devolvido
    @Operation(description = "Listar todos os epis com status devolvido")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Epis encontrados com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar epis"),
        ApiResponse(responseCode = "404", description = "Epis ou funcionario não encontrados"),
        ApiResponse(responseCode = "409", description = "Epis não devolvidos")
    )
    @GetMapping("/returned")
    fun getEmployeeEpiReturned() = employeeEpiService.getEmployeeEpiReturned()

    //devolver todos os epis de um funcionário
    @Operation(description = "Devolver todos os epis de um funcionário")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Epis devolvidos com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao devolver epis"),
        ApiResponse(responseCode = "404", description = "Funcionário não encontrado"),
        ApiResponse(responseCode = "409", description = "Epis não devolvidos"),
        ApiResponse(responseCode = "409", description = "Epis já devolvidos")
    )
    @PutMapping("/return")
    fun returnAllEpiToEmployee(@RequestBody returnAllEpi: ReturnAllEpi) = employeeEpiService.returnAllEpiToEmployee(returnAllEpi)
    //listar todos os epis de um funcionári
    @Operation(description = "Listar todos os epis de um funcionário")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Epis encontrados com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar epis"),
        ApiResponse(responseCode = "404", description = "Epis ou funcionario não encontrados")
    )
    @GetMapping("/employee/{employeeId}")
    fun getEmployeeEpiByEmployeeId(@PathVariable employeeId: UUID) = employeeEpiService.getEmployeeEpiByEmployeeId(employeeId)
    //listar todos os funcionarios com um epi
    @Operation(description = "Listar todos os funcionários com um epi")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Funcionários encontrados com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar funcionários"),
        ApiResponse(responseCode = "404", description = "Funcionários ou epi não encontrados")
    )
    @GetMapping("/epi/{epiId}")
    fun getEmployeeEpiByEpiId(@PathVariable epiId: UUID) = employeeEpiService.getEmployeeEpiByEpiId(epiId)
    //listar todos os epis que o status esteja entregue para um funcionário
    @Operation(description = "Listar todos os epis que o status esteja entregue para um funcionário")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Epis encontrados com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar epis"),
        ApiResponse(responseCode = "404", description = "Epis ou funcionario não encontrados"),
        ApiResponse(responseCode = "409", description = "Epis não entregues")
    )
    @GetMapping("/employeeDelivered/{employeeId}")
    fun getEmployeeEpiDeliveredByEmployeeId(@PathVariable employeeId: UUID) = employeeEpiService.getEmployeeEpiDeliveredByEmployeeId(employeeId)
}