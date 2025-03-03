package com.brayan.renovar.services.interfaces

import com.brayan.renovar.api.request.ToolsWorkResquest
import com.brayan.renovar.api.request.ToolsWorkUpdateResquest
import com.brayan.renovar.api.response.ToolsWorkResponse

interface ToolsWorkService {
    fun save(toolsWorkRequest: ToolsWorkResquest): ToolsWorkResponse
    fun update(toolsWorkRequest: ToolsWorkUpdateResquest): ToolsWorkResponse

}
