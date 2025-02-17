package com.brayan.renovar.database.repositories.interfaces

import com.brayan.renovar.models.ToolsEmployeesModel

interface ToolsEmployeeRepository {
    fun save(toolsEmployeeModel: ToolsEmployeesModel)

}
