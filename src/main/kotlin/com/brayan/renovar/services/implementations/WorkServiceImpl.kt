package com.brayan.renovar.services.implementations

import com.brayan.renovar.database.repositories.interfaces.WorkRepository
import com.brayan.renovar.models.WorkModel
import com.brayan.renovar.services.interfaces.WorkService
import org.springframework.stereotype.Service

@Service
class WorkServiceImpl (
    val workRepository: WorkRepository
): WorkService {
    override fun createWork(workModel: WorkModel): WorkModel {
        return workRepository.save(workModel)
    }


}