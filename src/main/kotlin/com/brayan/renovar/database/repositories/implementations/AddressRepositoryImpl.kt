package com.brayan.renovar.database.repositories.implementations

import com.brayan.renovar.database.entities.Address
import com.brayan.renovar.database.repositories.interfaces.AddressRepository
import com.brayan.renovar.database.repositories.springData.AddressSpringDataRepository
import com.brayan.renovar.models.AddressModel
import org.springframework.stereotype.Component
import java.util.UUID
@Component
class AddressRepositoryImpl(
    val addressSpring: AddressSpringDataRepository
): AddressRepository{
    override fun save(addressModel: AddressModel): AddressModel {
        val address = Address.of(addressModel)
        return addressSpring.save(address).toAddressModel()
    }

    override fun findById(id: UUID): AddressModel {
        return addressSpring.findById(id).map { it.toAddressModel() }.orElseThrow { Exception() }
    }
}