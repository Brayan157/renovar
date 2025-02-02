package com.brayan.renovar.services.interfaces

import com.brayan.renovar.api.request.AddressUpdateRequest
import com.brayan.renovar.models.AddressModel

interface AddressService {
    fun createAddress(addressModel: AddressModel): AddressModel
    fun updateAddress(addressUpdateRequest: AddressUpdateRequest): AddressModel

}
