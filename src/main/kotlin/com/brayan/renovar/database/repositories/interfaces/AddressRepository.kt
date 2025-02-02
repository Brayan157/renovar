package com.brayan.renovar.database.repositories.interfaces

import com.brayan.renovar.models.AddressModel
import java.util.UUID

interface AddressRepository {
    fun save(addressModel: AddressModel): AddressModel
    fun findById(id: UUID): AddressModel

}
