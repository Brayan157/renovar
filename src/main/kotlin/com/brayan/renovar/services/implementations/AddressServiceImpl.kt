package com.brayan.renovar.services.implementations

import com.brayan.renovar.api.request.AddressUpdateRequest
import com.brayan.renovar.database.repositories.interfaces.AddressRepository
import com.brayan.renovar.models.AddressModel
import com.brayan.renovar.services.interfaces.AddressService
import org.springframework.stereotype.Service

@Service
class AddressServiceImpl(
    val AddressRepository: AddressRepository
): AddressService {
    override fun createAddress(addressModel: AddressModel): AddressModel {
        return AddressRepository.save(addressModel)
    }

    override fun updateAddress(addressUpdateRequest: AddressUpdateRequest): AddressModel {
        val address = AddressRepository.findById(addressUpdateRequest.id!!)
        val addressModel = address.copy(
            street = addressUpdateRequest.street ?: address.street,
            number = addressUpdateRequest.number ?: address.number,
            complement = addressUpdateRequest.complement ?: address.complement,
            neighborhood = addressUpdateRequest.neighborhood ?: address.neighborhood,
            city = addressUpdateRequest.city ?: address.city,
            state = addressUpdateRequest.state ?: address.state,
            zipCode = addressUpdateRequest.zipCode  ?: address.zipCode
        )
        return AddressRepository.save(addressModel)
    }


}