package com.brayan.renovar.api.controller

import com.brayan.renovar.models.WorkModel
import com.brayan.renovar.services.interfaces.WorkService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/work")
class WorkController(
    val workService: WorkService
) {
    @PostMapping
    fun createWork(@RequestBody workModel: WorkModel) = workService.createWork(workModel)
}