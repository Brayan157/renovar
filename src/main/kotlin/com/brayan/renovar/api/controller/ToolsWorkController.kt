package com.brayan.renovar.api.controller

import com.brayan.renovar.api.request.ToolsWorkResquest
import com.brayan.renovar.api.request.ToolsWorkUpdateResquest
import com.brayan.renovar.services.interfaces.ToolsWorkService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/toolsWork")
class ToolsWorkController(
    val toolsWorkService: ToolsWorkService
) {
    @PostMapping
    fun save(@RequestBody toolsWorkRequest: ToolsWorkResquest) = toolsWorkService.save(toolsWorkRequest)
    @PutMapping
    fun update(@RequestBody toolsWorkRequest: ToolsWorkUpdateResquest) = toolsWorkService.update(toolsWorkRequest)
}