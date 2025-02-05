package com.brayan.renovar.api.request

import com.brayan.renovar.enum.EPIStatus
import java.time.LocalDate
import java.util.UUID

data class ReturnEpi(
    val episQuantidade:Map<UUID, Long>,
    val employeeId: UUID,
    val returnDate: LocalDate,
    val epiStatus: EPIStatus = EPIStatus.DEVOLVIDO
)
