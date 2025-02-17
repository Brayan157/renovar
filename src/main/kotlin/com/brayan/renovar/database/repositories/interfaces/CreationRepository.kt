package com.brayan.renovar.database.repositories.interfaces

import java.util.UUID

interface CreationRepository {
     fun saveCreation(): UUID?
     fun findCreationById(id: UUID): UUID?

}
