package com.brayan.renovar.api.controller

import com.brayan.renovar.api.request.EPIUpdateRequest
import com.brayan.renovar.models.EPIModel
import com.brayan.renovar.services.interfaces.EPIService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/epi")
class EPIController (
    val epiService: EPIService
){
    @PostMapping
    fun saveEPI(@RequestBody epi: EPIModel) = epiService.save(epi)
    @PutMapping
    fun updateEPI(@RequestBody epi: EPIUpdateRequest) = epiService.update(epi)
    //Listar todos os EPIs com expirationDate menor que a data atual
    @GetMapping("/expired")
    fun listExpiredEPIs() = epiService.listExpiredEPIs()
    @GetMapping
    fun listAllEPIs() = epiService.listAllEPIs()
    @GetMapping("id/{id}")
    fun findById(@PathVariable id: UUID) = epiService.findById(id)
    @GetMapping("name/{name}")
    fun findByName(@PathVariable name: String) = epiService.findByName(name)
    @GetMapping("notExpired")
    fun listNotExpiredEPIs() = epiService.listNotExpiredEPIs()
}