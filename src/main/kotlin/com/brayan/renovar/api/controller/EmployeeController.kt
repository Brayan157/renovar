package com.brayan.renovar.api.controller

import com.brayan.renovar.api.request.EmployeeUpdateRequest
import com.brayan.renovar.api.request.FunctionRequest
import com.brayan.renovar.enum.EmployeeStatus
import com.brayan.renovar.models.EmployeeModel
import com.brayan.renovar.services.interfaces.EmployeeService
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
@RequestMapping("/employee")
class EmployeeController(
    private val employeeService: EmployeeService
) {
    @Operation(description = "Criar uma nova função")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Função criada com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao criar função")
    )
    @PostMapping("/function")
    fun createFunction(@RequestBody functionRequest: FunctionRequest) = employeeService.createFunction(functionRequest)
    @Operation(description = "Buscar função por id")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Função encontrada com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar função"),
        ApiResponse(responseCode = "404", description = "Função não encontrada")
    )
    @GetMapping("/function/id/{id}")
    fun getFunctionById(@PathVariable id: UUID) = employeeService.getFunctionById(id)
    @Operation(description = "Buscar função por nome")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Função encontrada com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar função"),
        ApiResponse(responseCode = "404", description = "Função não encontrada")
    )
    @GetMapping("/function/name/{name}")
    fun getFunctionByName(@PathVariable name: String) = employeeService.getFunctionByName(name)
    @Operation(description = "Buscar todas as funções")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Funções encontradas com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar funções")
    )
    @GetMapping("/function")
    fun getFunctions() = employeeService.getFunctions()
    @Operation(description = "Criar um novo funcionário")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Funcionário criado com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao criar funcionário"),
        ApiResponse(responseCode = "404", description = "Função não encontrada")
    )
    @PostMapping
    fun createEmployee(@RequestBody employeeModel: EmployeeModel) = employeeService.createEmployee(employeeModel)
    @Operation(description = "Buscar todos os funcionários")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Funcionários encontrados com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar funcionários"),
        ApiResponse(responseCode = "404", description = "Funcionários não encontrados")
    )
    @GetMapping
    fun getEmployees() = employeeService.getEmployees()
    @Operation(description = "Buscar funcionário por id")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Funcionário encontrado com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar funcionário"),
        ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
    )
    @GetMapping("/{employeeId}")
    fun getEmployeeById(@PathVariable employeeId: UUID) = employeeService.getEmployeeById(employeeId)
    @Operation(description = "Atualizar um funcionário")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Funcionário atualizado com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao atualizar funcionário"),
        ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
    )
    @PutMapping
    fun updateEmployee(@RequestBody employeeRequest: EmployeeUpdateRequest) = employeeService.updateEmployee(employeeRequest)
    @Operation(description = "Buscar funcionários por status")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Funcionários encontrados com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar funcionários"),
        ApiResponse(responseCode = "404", description = "Funcionários não encontrados")
    )
    @GetMapping("/status/{status}")
    fun getEmployeesByStatus(@PathVariable status: EmployeeStatus) = employeeService.getEmployeesByStatus(status)
    @Operation(description = "Buscar funcionários por nome")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Funcionários encontrados com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar funcionários"),
        ApiResponse(responseCode = "404", description = "Funcionários não encontrados")
    )
    @GetMapping("/name/{name}")
    fun getEmployeesByName(@PathVariable name:String) = employeeService.getEmployeesByName(name)
}
