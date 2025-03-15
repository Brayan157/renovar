package com.brayan.renovar.api.controller

import com.brayan.renovar.api.request.ToolsWorkResquest
import com.brayan.renovar.api.request.ToolsWorkUpdateResquest
import com.brayan.renovar.database.entities.ToolsWorkId
import com.brayan.renovar.services.interfaces.ToolsWorkService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/toolsWork")
class ToolsWorkController(
    val toolsWorkService: ToolsWorkService
) {
    @PostMapping
    fun save(@RequestBody toolsWorkRequest: ToolsWorkResquest) = toolsWorkService.save(toolsWorkRequest)
    @PutMapping
    fun update(@RequestBody toolsWorkRequest: ToolsWorkUpdateResquest) = toolsWorkService.update(toolsWorkRequest)
    @GetMapping
    fun listAll() = toolsWorkService.listAll()
    @GetMapping("/work/{workId}")
    fun listByWorkId(@PathVariable workId: UUID) = toolsWorkService.listByWorkId(workId)
    @GetMapping("/work/{workId}/status/loaned")
    fun listByWorkIdAndStatusLoaned(@PathVariable workId: UUID) = toolsWorkService.listByWorkIdAndStatusLoaned(workId)
    @GetMapping("/work/{workId}/status/returned")
    fun listByWorkIdAndStatusReturned(@PathVariable workId: UUID) = toolsWorkService.listByWorkIdAndStatusReturned(workId)
    //listar uma ferramenta de uma obra
    @GetMapping("/workTool")
    fun listByWorkIdAndToolId(@RequestBody toolsWorkId: ToolsWorkId) = toolsWorkService.listByWorkIdAndToolId(toolsWorkId)

}