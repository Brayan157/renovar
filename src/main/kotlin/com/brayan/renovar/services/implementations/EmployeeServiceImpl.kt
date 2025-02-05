package com.brayan.renovar.services.implementations

import com.brayan.renovar.api.request.ADDEPISEmployee
import com.brayan.renovar.api.request.AddToolsForEmployeeRequest
import com.brayan.renovar.api.request.EmployeeUpdateRequest
import com.brayan.renovar.api.request.FunctionRequest
import com.brayan.renovar.api.request.RemoveToolEmployeeRequest
import com.brayan.renovar.api.request.ReturnEpi
import com.brayan.renovar.database.repositories.interfaces.EPIRepository
import com.brayan.renovar.database.repositories.interfaces.EmployeeRepository
import com.brayan.renovar.database.repositories.interfaces.ToolRepository
import com.brayan.renovar.enum.EmployeeStatus
import com.brayan.renovar.models.EmployeeEPIModel
import com.brayan.renovar.models.EmployeeModel
import com.brayan.renovar.models.FunctionModel
import com.brayan.renovar.models.ToolsEmployeesModel
import com.brayan.renovar.services.interfaces.EmployeeService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class EmployeeServiceImpl(
    val EmployeeRepository: EmployeeRepository,
    val epiRepository: EPIRepository,
    val toolRepository: ToolRepository,
):EmployeeService{
    override fun createFunction(functionRequest: FunctionRequest): FunctionModel {
        val functionModel = FunctionModel(
            function = functionRequest.name,
            description = functionRequest.description
        )
        return EmployeeRepository.save(functionModel)
    }

    override fun getFunctionById(id: UUID): FunctionModel {
        return EmployeeRepository.findById(id)
    }

    override fun getFunctionByName(name: String): List<FunctionModel> {
        return EmployeeRepository.findByName(name)
    }

    override fun getFunctions(): List<FunctionModel> {
        return EmployeeRepository.findAll()
    }

    override fun createEmployee(employeeModel: EmployeeModel): EmployeeModel {
        val employee = employeeModel.copy(
            registration = generateRegistration()
        )
        return EmployeeRepository.saveEmployee(employee)
    }

    override fun generateRegistration(): String {
        return EmployeeRepository.findLastRegistration()
    }

    override fun getEmployees(): List<EmployeeModel> {
        return EmployeeRepository.findAllEmployees()
    }

    override fun getEmployeeById(employeeId: UUID): EmployeeModel {
        return EmployeeRepository.findEmployeeById(employeeId)
    }

    override fun updateEmployee(employeeRequest: EmployeeUpdateRequest): EmployeeModel {
        val employeeModel = getEmployeeById(employeeRequest.id)
        val employee = employeeModel.copy(
            name = employeeRequest.name?: employeeModel.name,
            functionModel = employeeRequest.functionModel?: employeeModel.functionModel,
            phones = employeeRequest.phones?: employeeModel.phones,
            hourlyRate = employeeRequest.hourlyRate?: employeeModel.hourlyRate,
            employeeStatus = employeeRequest.status?: employeeModel.employeeStatus
        )
        return EmployeeRepository.saveEmployee(employee)
    }

    override fun getEmployeesByStatus(status: EmployeeStatus): List<EmployeeModel> {
        return EmployeeRepository.findAllByStatus(status)
    }

    override fun getEmployeesByName(name: String): List<EmployeeModel> {
        return EmployeeRepository.findAllByName(name)
    }

    override fun findAllIds(ids:List<UUID>): List<EmployeeModel> {
        return EmployeeRepository.findAllById(ids).map { it.toEmployeeModel() }
    }

    override fun addEPI(addepisEmployee: ADDEPISEmployee): EmployeeModel {
        val employee = getEmployeeById(addepisEmployee.employeeId)
        val episList = mutableListOf<EmployeeEPIModel>()
        addepisEmployee.episQuantidade.forEach {
            val epi = epiRepository.findById(it.key)
            episList.add(EmployeeEPIModel(
                employeeId = employee.id!!,
                epiId = epi.id!!,
                quantity = it.value.toInt(),
                deliveryDate = addepisEmployee.deliveryDate,
                epiStatus = addepisEmployee.epiStatus,
                reason = addepisEmployee.reason
            ))
            val newEpi = epi.copy(
                quantity = epi.quantity - it.value.toInt()
            )
            epiRepository.save(newEpi)
        }
        val employeeModel = employee.copy(
            employeeEpis = episList
        )

        return EmployeeRepository.saveEmployee(employeeModel)
    }

    override fun returnEPI(returnEpi: ReturnEpi): EmployeeModel {
        val employee = getEmployeeById(returnEpi.employeeId)
        val episList = mutableListOf<EmployeeEPIModel>()
        returnEpi.episQuantidade.forEach {
            val epi = epiRepository.findById(it.key)
            episList.add(EmployeeEPIModel(
                employeeId = employee.id!!,
                epiId = epi.id!!,
                quantity = it.value.toInt(),
                deliveryDate = employee.employeeEpis.find { it.epiId == it.epiId }?.deliveryDate ?: throw Exception("EPI not found"),
                epiStatus = returnEpi.epiStatus,
                reason = employee.employeeEpis.find { it.epiId == it.epiId }?.reason ?: throw Exception("EPI not found"),
                returnDate = returnEpi.returnDate
            ))
        }
        val employeeModel = employee.copy(
            employeeEpis = episList
        )

        return EmployeeRepository.saveEmployee(employeeModel)
    }

    override fun addToolsForEmployee(addToolsForEmployeeRequest: AddToolsForEmployeeRequest): EmployeeModel {
        val employee = getEmployeeById(addToolsForEmployeeRequest.employeeId)
        val toolsList = mutableListOf<ToolsEmployeesModel>()
        addToolsForEmployeeRequest.tools.forEach {
            val tool = toolRepository.findById(it.key)
            toolsList.add(ToolsEmployeesModel(
                toolId = tool.id!!,
                employeeId = employee.id!!,
                startDate = addToolsForEmployeeRequest.startDate,
                quantity = it.value.toInt(),
                status = addToolsForEmployeeRequest.status
            ))
            var quantity:Int = 0
            if (tool.quantity != null){
                if (tool.quantity!! < it.value.toInt()){
                    throw Exception("Quantity of tools is not enough")
                }
                quantity = tool.quantity!! - it.value.toInt()
            }
            val newTool = tool.copy(
                quantity = quantity
            )
            toolRepository.save(newTool)
        }
        val employeeModel = employee.copy(
            toolsEmployee = toolsList
        )
        return EmployeeRepository.saveEmployee(employeeModel)
    }

    override fun removeToolsForEmployee(removeToolsFroEmployee: RemoveToolEmployeeRequest): EmployeeModel {
        val employee = getEmployeeById(removeToolsFroEmployee.employeeId)
        val toolsList = mutableListOf<ToolsEmployeesModel>()
        removeToolsFroEmployee.tools.forEach {
            val tool = toolRepository.findById(it.key)
            toolsList.add(ToolsEmployeesModel(
                toolId = tool.id!!,
                employeeId = employee.id!!,
                startDate = employee.toolsEmployee.find { it.toolId == it.toolId }?.startDate ?: throw Exception("Tool not found"),
                quantity = it.value.toInt(),
                status = removeToolsFroEmployee.status
            ))
            val newTool = tool.copy(
                quantity = tool.quantity!! + it.value.toInt()
            )
            toolRepository.save(newTool)
        }
        val employeeModel = employee.copy(
            toolsEmployee = toolsList
        )
        return EmployeeRepository.saveEmployee(employeeModel)
    }

}