package com.brayan.renovar.api.controller

import com.brayan.renovar.api.request.AddressUpdateRequest
import com.brayan.renovar.models.AddressModel
import com.brayan.renovar.services.interfaces.AddressService
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
@RequestMapping("/address")
class AddressController(
    private val addressService: AddressService
) {
    @Operation(description = "Criar um novo endereço")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Endereço criado com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao criar endereço")
    )
    @PostMapping
    fun createAddress(@RequestBody addressModel: AddressModel) = addressService.createAddress(addressModel)
    @Operation(description = "Atualizar um endereço")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao atualizar endereço"),
        ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    )
    @PutMapping
    fun updateAddress(@RequestBody addressUpdateRequest: AddressUpdateRequest) = addressService.updateAddress(addressUpdateRequest)
    @Operation(description = "Buscar endereço por id")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso"),
        ApiResponse(responseCode = "400", description = "Erro ao buscar endereço"),
        ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    )
    @GetMapping("id/{id}")
    fun getAddressById(@PathVariable id: UUID) = addressService.getAddressById(id)
}