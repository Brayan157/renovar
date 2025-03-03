package com.brayan.renovar.api.request

import com.brayan.renovar.enum.ToolStatus
import java.util.UUID

data class ToolUpdateStatusRequest(
    val id: UUID,
    val toolStatus: ToolStatus? = null
)
