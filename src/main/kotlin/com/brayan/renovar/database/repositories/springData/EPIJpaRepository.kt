package com.brayan.renovar.database.repositories.springData

import com.brayan.renovar.database.entities.EPI
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface EPIJpaRepository:JpaRepository<EPI, UUID> {

}
