package com.brayan.renovar.api.controller

import com.brayan.renovar.api.request.ToolUpdateRequest
import com.brayan.renovar.api.request.ToolUpdateStatusRequest
import com.brayan.renovar.enum.ToolStatus
import com.brayan.renovar.models.ToolModel
import com.brayan.renovar.services.interfaces.ToolsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/tools")
class ToolsController(
    val toolsService: ToolsService
) {
    @PostMapping
    fun save(@RequestBody toolModel: ToolModel) = toolsService.save(toolModel)
    @PutMapping
    fun update(@RequestBody toolUpdade: ToolUpdateRequest) = toolsService.update(toolUpdade)
    @GetMapping
    fun getAll() = toolsService.findAll()
    @GetMapping("/id/{id}")
    fun getById(@PathVariable id: UUID) = toolsService.findById(id)
    @GetMapping("/name/{name}")
    fun getByName(@PathVariable name: String) = toolsService.findByName(name)
    @GetMapping("status")
    fun getByStatus(@RequestBody status: ToolStatus) = toolsService.findByStatus(status)
    @PutMapping("/delete/{id}")
    fun deleteById(@PathVariable id: UUID) = toolsService.deleteById(id)
    @GetMapping("/status/all")
    fun getAllToolsExceptDeleted() = toolsService.findByStatusNot(ToolStatus.DELETADA)
}