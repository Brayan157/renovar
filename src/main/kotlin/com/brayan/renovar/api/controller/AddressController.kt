package com.brayan.renovar.api.controller

import com.brayan.renovar.api.request.AddressUpdateRequest
import com.brayan.renovar.models.AddressModel
import com.brayan.renovar.services.interfaces.AddressService
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
    @PostMapping
    fun createAddress(@RequestBody addressModel: AddressModel) = addressService.createAddress(addressModel)

    @PutMapping
    fun updateAddress(@RequestBody addressUpdateRequest: AddressUpdateRequest) = addressService.updateAddress(addressUpdateRequest)
    @GetMapping("id/{id}")
    fun getAddressById(@PathVariable id: UUID) = addressService.getAddressById(id)
}