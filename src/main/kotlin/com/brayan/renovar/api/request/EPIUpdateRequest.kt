package com.brayan.renovar.api.request

import java.time.LocalDate
import java.util.UUID

data class EPIUpdateRequest(
    val id: UUID,
    val name: String? = null,
    val approvalCertificate: String? = null,
    val quantity: Int? = null,
    val unitValue: Double? = null,
    val manufacturingDate: LocalDate? = null,
    val expirationDate: LocalDate? = null,
    val tag:String? = null,
    val lot:String? = null

)
