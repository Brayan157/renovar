package com.brayan.renovar.database.repositories.springData

import com.brayan.renovar.database.entities.Address
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AddressSpringDataRepository:JpaRepository<Address, UUID> {
}