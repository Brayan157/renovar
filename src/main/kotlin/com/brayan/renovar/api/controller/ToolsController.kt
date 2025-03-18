package com.brayan.renovar.api.controller

import com.brayan.renovar.api.request.ToolUpdateRequest
import com.brayan.renovar.api.request.ToolUpdateStatusRequest
import com.brayan.renovar.enum.ToolStatus
import com.brayan.renovar.models.ToolModel
import com.brayan.renovar.services.interfaces.ToolsService
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
@RequestMapping("/tools")
class ToolsController(
    val toolsService: ToolsService
) {
    @Operation(description = "Salvar uma nova ferramenta")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Ferramenta salva com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao salvar ferramenta")
    )
    @PostMapping
    fun save(@RequestBody toolModel: ToolModel) = toolsService.save(toolModel)
    @Operation(description = "Atualizar uma ferramenta")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Ferramenta atualizada com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao atualizar ferramenta"),
        ApiResponse(responseCode = "404", description = "Ferramenta não encontrada")
    )
    @PutMapping
    fun update(@RequestBody toolUpdade: ToolUpdateRequest) = toolsService.update(toolUpdade)
    @Operation(description = "Listar todas as ferramentas")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Ferramentas encontradas com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar ferramentas")
    )
    @GetMapping
    fun getAll() = toolsService.findAll()
    @Operation(description = "Buscar uma ferramenta por id")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Ferramenta encontrada com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar ferramenta"),
        ApiResponse(responseCode = "404", description = "Ferramenta não encontrada")
    )
    @GetMapping("/id/{id}")
    fun getById(@PathVariable id: UUID) = toolsService.findById(id)
    @Operation(description = "Listar ferramentas por nome")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Ferramenta encontrada com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar ferramenta"),
        ApiResponse(responseCode = "404", description = "Ferramenta não encontrada")
    )
    @GetMapping("/name/{name}")
    fun getByName(@PathVariable name: String) = toolsService.findByName(name)
    @Operation(description = "Listar ferramentas por status")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Status da ferramenta atualizado com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao atualizar status da ferramenta"),
        ApiResponse(responseCode = "404", description = "Ferramenta não encontrada")
    )
    @GetMapping("status")
    fun getByStatus(@RequestBody status: ToolStatus) = toolsService.findByStatus(status)
    @Operation(description = "Deletar uma ferramenta")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Ferramenta deletada com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao deletar ferramenta"),
        ApiResponse(responseCode = "404", description = "Ferramenta não encontrada")
    )
    @PutMapping("/delete/{id}")
    fun deleteById(@PathVariable id: UUID) = toolsService.deleteById(id)
    @Operation(description = "Listar todas as ferramentas exceto as deletadas")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Lista de ferramentas encontrada com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar lista de ferramentas"),
    )
    @GetMapping("/status/all")
    fun getAllToolsExceptDeleted() = toolsService.findByStatusNot(ToolStatus.DELETADA)
}