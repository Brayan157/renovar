package com.brayan.renovar.api.request

import com.brayan.renovar.enum.EPIStatus
import java.time.LocalDate
import java.util.UUID

data class ADDEPISEmployee(
    val episQuantidade:Map<UUID, Long>,
    val employeeId: UUID,
    val deliveryDate: LocalDate,
    val epiStatus: EPIStatus = EPIStatus.ENTREGUE,
    val reason: String
)
