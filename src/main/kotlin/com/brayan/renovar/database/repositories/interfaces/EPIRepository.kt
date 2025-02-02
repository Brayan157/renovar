package com.brayan.renovar.database.repositories.interfaces

import com.brayan.renovar.database.entities.EPI
import java.util.UUID

interface EPIRepository {
    fun findAllById (ids: List<UUID>): List<EPI>

}
