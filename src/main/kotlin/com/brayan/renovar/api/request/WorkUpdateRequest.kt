package com.brayan.renovar.api.request

import com.brayan.renovar.enum.WorkStatus
import java.util.UUID

data class WorkUpdateRequest(
    val id: UUID,
    val companyProviding: String? = null,
    val workStatus: WorkStatus? = null

)
