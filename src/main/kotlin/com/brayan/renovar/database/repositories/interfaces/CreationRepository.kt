package com.brayan.renovar.database.repositories.interfaces

import com.brayan.renovar.database.entities.CreationDate
import java.util.UUID

interface CreationRepository {
     fun saveCreation(): UUID?
     fun findCreationById(id: UUID): CreationDate

}
