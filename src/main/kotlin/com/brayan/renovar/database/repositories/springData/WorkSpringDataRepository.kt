package com.brayan.renovar.database.repositories.springData

import com.brayan.renovar.database.entities.Work
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface WorkSpringDataRepository:JpaRepository<Work, UUID> {

}
