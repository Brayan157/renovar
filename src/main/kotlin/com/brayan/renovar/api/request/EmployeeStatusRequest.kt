package com.brayan.renovar.api.request

import com.brayan.renovar.enum.EmployeeStatus

data class EmployeeStatusRequest (
    val status: EmployeeStatus
)

