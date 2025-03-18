package com.brayan.renovar.api.controller

import com.brayan.renovar.api.request.AddEmployeesWorkRequest
import com.brayan.renovar.api.request.ReturnEmployeeToWork
import com.brayan.renovar.database.entities.EmployeeWorkKey
import com.brayan.renovar.services.interfaces.EmployeeWorkService
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
@RequestMapping("/employeeWork")
class EmployeeWorkController(
    val employeeWorkService: EmployeeWorkService
) {
    @Operation(description = "Adicionar um funcionário a uma obra")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Funcionário adicionado com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao adicionar funcionário"),
        ApiResponse(responseCode = "404", description = "Funcionário ou obra não encontrado")
    )
    @PostMapping
    fun saveEmployeeWork(@RequestBody addEmployeesWorkRequest: AddEmployeesWorkRequest) = employeeWorkService.saveEmployeeWork(addEmployeesWorkRequest)
    @Operation(description = "Devolver um funcionário de uma obra")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Funcionário devolvido com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao devolver funcionário"),
        ApiResponse(responseCode = "404", description = "Funcionário ou obra não encontrado"),
        ApiResponse(responseCode = "409", description = "Funcionário já devolvido")
    )
    @PutMapping
    fun updateEmployeeWork(@RequestBody returnEmployeeToWork: ReturnEmployeeToWork) = employeeWorkService.updateEmployeeWork(returnEmployeeToWork)
    @Operation(description = "Listar todos os funcionários em obras")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Funcionários encontrados com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar funcionários"),
        ApiResponse(responseCode = "404", description = "Funcionários não encontrados")
    )
    @GetMapping
    fun getEmployeeWork() = employeeWorkService.getEmployeeWork()
    @Operation(description = "Buscar um funcionário em uma obra")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Funcionário encontrado com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar funcionário"),
        ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
    )
    @GetMapping("/id")
    fun getEmployeeWorkById(@RequestBody id: EmployeeWorkKey) = employeeWorkService.getEmployeeWorkById(id)

    //listar somente os funcionários que estão trabalhando
    @Operation(description = "Listar todos os funcionários que estão trabalhando")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Funcionários encontrados com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar funcionários"),
        ApiResponse(responseCode = "404", description = "Funcionários não encontrados")
    )
    @GetMapping("/working")
    fun getEmployeeWorkWorking() = employeeWorkService.getEmployeeWorkWorking()
    //listar somente os funcionários que não estão trabalhando
    @Operation(description = "Listar todos os funcionários que não estão trabalhando")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Funcionários encontrados com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar funcionários"),
        ApiResponse(responseCode = "404", description = "Funcionários não encontrados")
    )
    @GetMapping("/notWorking")
    fun getEmployeeWorkNotWorking() = employeeWorkService.getEmployeeWorkNotWorking()

    //listar obra que o funcionário está trabalhando
    @GetMapping("/work/{employeeId}")
    @Operation(description = "Listar obra que o funcionário está trabalhando")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Obra encontrada com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar obra"),
        ApiResponse(responseCode = "404", description = "Obra não encontrada")
    )
    fun getEmployeeWorkByEmployeeId(@PathVariable employeeId: UUID) = employeeWorkService.getEmployeeWorkByEmployeeId(employeeId)
    //listar todos os funcionários que estão trabalhando em uma obra
    @Operation(description = "Listar todos os funcionários que estão trabalhando em uma obra")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Funcionários encontrados com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar funcionários"),
        ApiResponse(responseCode = "404", description = "Funcionários não encontrados")
    )
    @GetMapping("/workId/{workId}")
    fun getEmployeeWorkByWorkId(@PathVariable workId: UUID) = employeeWorkService.getEmployeeWorkByWorkId(workId)
}