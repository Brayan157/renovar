package com.brayan.renovar.api.request

import com.brayan.renovar.enum.EmployeeStatus
import com.brayan.renovar.models.FunctionModel
import java.util.UUID

data class EmployeeUpdateRequest(
    val id:UUID,
    val name:String? = null,
    val functionModel: FunctionModel? = null,
    val phones: String? = null,
    val hourlyRate: Double? = null,
    val status: EmployeeStatus? = null
)
