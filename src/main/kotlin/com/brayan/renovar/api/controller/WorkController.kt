package com.brayan.renovar.api.controller

import com.brayan.renovar.api.request.WorkUpdateRequest
import com.brayan.renovar.api.request.AddEmployeesWorkRequest
import com.brayan.renovar.api.request.AddToolWorkRequest
import com.brayan.renovar.api.request.RemoveEmployeesWork
import com.brayan.renovar.api.request.RemoveToolWorkRequest
import com.brayan.renovar.enum.WorkStatus
import com.brayan.renovar.models.WorkModel
import com.brayan.renovar.services.interfaces.WorkService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/work")
class WorkController(
    val workService: WorkService
) {
    @PostMapping
    fun createWork(@RequestBody workModel: WorkModel) = workService.createWork(workModel)
    @PutMapping
    fun updateWork(@RequestBody workUpdate: WorkUpdateRequest) = workService.updateWork(workUpdate)
    @GetMapping
    fun getWorks() = workService.getWorks()

    @GetMapping("/{workId}")
    fun getWorkById(@PathVariable workId:UUID) = workService.getWorkById(workId)
    @GetMapping("/status")
    fun getWorksByStatus(@RequestBody status: WorkStatus) = workService.getWorksByStatus(status)
    @GetMapping("/companyProviding/{companyProviding}")
    fun getWorksByCompanyProviding(@PathVariable companyProviding: String) = workService.getWorksByCompanyProviding(companyProviding)

    @PutMapping("/addEmployee")
    fun addEmployeeToWork(@RequestBody addEmployeesByWork: AddEmployeesWorkRequest) = workService.addEmployeeToWork(addEmployeesByWork)
    @PutMapping("/removeEmployeeWork")
    fun removeEmployeeFromWork(@RequestBody removeEmployeesWork: RemoveEmployeesWork) = workService.removeEmployeeFromWork(removeEmployeesWork)

    @PutMapping("addToolWork")
    fun addToolToWork(@RequestBody addToolWork: AddToolWorkRequest) = workService.addToolToWork(addToolWork)
    @PutMapping("removeToolWork")
    fun removeToolFromWork(@RequestBody removeToolWork: RemoveToolWorkRequest) = workService.removeToolFromWork(removeToolWork)
}