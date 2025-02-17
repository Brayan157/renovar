package com.brayan.renovar.database.repositories.springData

import com.brayan.renovar.database.entities.CreationDate
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CreationSpringDataRepository : JpaRepository<CreationDate, UUID> {

}
