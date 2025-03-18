package com.brayan.renovar.api.controller

import com.brayan.renovar.api.request.EPIUpdateRequest
import com.brayan.renovar.models.EPIModel
import com.brayan.renovar.services.interfaces.EPIService
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
@RequestMapping("/epi")
class EPIController (
    val epiService: EPIService
){
    @Operation(description = "Salvar um novo EPI")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "EPI salvo com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao salvar EPI")
    )
    @PostMapping
    fun saveEPI(@RequestBody epi: EPIModel) = epiService.save(epi)
    @Operation(description = "Atualizar um EPI")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "EPI atualizado com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao atualizar EPI"),
        ApiResponse(responseCode = "404", description = "EPI não encontrado")
    )
    @PutMapping
    fun updateEPI(@RequestBody epi: EPIUpdateRequest) = epiService.update(epi)
    //Listar todos os EPIs com expirationDate menor que a data atual
    @Operation(description = "Listar todos os EPIs com expirationDate menor que a data atual")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "EPIs encontrados com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar EPIs"),
        ApiResponse(responseCode = "404", description = "EPIs não encontrados")
    )
    @GetMapping("/expired")
    fun listExpiredEPIs() = epiService.listExpiredEPIs()
    @Operation(description = "Listar todos os EPIs")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "EPIs encontrados com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar EPIs"),
        ApiResponse(responseCode = "404", description = "EPIs não encontrados")
    )
    @GetMapping
    fun listAllEPIs() = epiService.listAllEPIs()
    @Operation(description = "Buscar um EPI por id")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "EPI encontrado com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar EPI"),
        ApiResponse(responseCode = "404", description = "EPI não encontrado")
    )
    @GetMapping("id/{id}")
    fun findById(@PathVariable id: UUID) = epiService.findById(id)
    @Operation(description = "Buscar um EPI por nome")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "EPI encontrado com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar EPI"),
        ApiResponse(responseCode = "404", description = "EPI não encontrado")
    )
    @GetMapping("name/{name}")
    fun findByName(@PathVariable name: String) = epiService.findByName(name)
    @Operation(description = "Listar todos os EPIs não expirados")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "EPIs encontrados com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar EPIs"),
        ApiResponse(responseCode = "404", description = "EPIs não encontrados")
    )
    @GetMapping("notExpired")
    fun listNotExpiredEPIs() = epiService.listNotExpiredEPIs()
}