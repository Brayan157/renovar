package com.brayan.renovar.api.controller

import com.brayan.renovar.api.request.ToolsWorkResquest
import com.brayan.renovar.api.request.ToolsWorkUpdateResquest
import com.brayan.renovar.database.entities.ToolsWorkId
import com.brayan.renovar.services.interfaces.ToolsWorkService
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
@RequestMapping("/toolsWork")
class ToolsWorkController(
    val toolsWorkService: ToolsWorkService
) {
    @Operation(description = "Salvar uma nova ferramenta de uma obra")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Ferramenta salva com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao salvar ferramenta")
    )
    @PostMapping
    fun save(@RequestBody toolsWorkRequest: ToolsWorkResquest) = toolsWorkService.save(toolsWorkRequest)
    @Operation(description = "Atualizar uma ferramenta de uma obra")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Ferramenta atualizada com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao atualizar ferramenta"),
        ApiResponse(responseCode = "404", description = "Ferramenta não encontrada")
    )
    @PutMapping
    fun update(@RequestBody toolsWorkRequest: ToolsWorkUpdateResquest) = toolsWorkService.update(toolsWorkRequest)
    @Operation(description = "Listar todas as ferramentas de uma obra")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Ferramentas encontradas com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar ferramentas")
    )
    @GetMapping
    fun listAll() = toolsWorkService.listAll()
    @Operation(description = "Listar todas as ferramentas de uma obra por id")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Ferramentas encontradas com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar ferramentas")
    )
    @GetMapping("/work/{workId}")
    fun listByWorkId(@PathVariable workId: UUID) = toolsWorkService.listByWorkId(workId)
    @Operation(description = "Listar todas as ferramentas de uma obra por id e status")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Ferramentas encontradas com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar ferramentas")
    )
    @GetMapping("/work/{workId}/status/loaned")
    fun listByWorkIdAndStatusLoaned(@PathVariable workId: UUID) = toolsWorkService.listByWorkIdAndStatusLoaned(workId)
    @Operation(description = "Listar todas as ferramentas de uma obra por id e status")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Ferramentas encontradas com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar ferramentas")
    )
    @GetMapping("/work/{workId}/status/returned")
    fun listByWorkIdAndStatusReturned(@PathVariable workId: UUID) = toolsWorkService.listByWorkIdAndStatusReturned(workId)
    @Operation(description = "Listar todas as ferramentas de uma obra por id e status")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Ferramentas encontradas com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar ferramentas")
    )
    //listar uma ferramenta de uma obra
    @GetMapping("/workTool")
    fun listByWorkIdAndToolId(@RequestBody toolsWorkId: ToolsWorkId) = toolsWorkService.listByWorkIdAndToolId(toolsWorkId)

}