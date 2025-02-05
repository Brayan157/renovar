package com.brayan.renovar.api.request

import java.time.LocalDate
import java.util.UUID

data class AddToolWorkRequest(
    val toolIds: List<UUID>,
    val workId: UUID,
    val reason:String,
    val entryDate:LocalDate
)
