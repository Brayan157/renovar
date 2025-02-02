package com.brayan.renovar.services.interfaces

import com.brayan.renovar.models.WorkModel

interface WorkService {
    fun createWork(workModel: WorkModel): WorkModel

}
