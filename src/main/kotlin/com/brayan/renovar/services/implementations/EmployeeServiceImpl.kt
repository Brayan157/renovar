package com.brayan.renovar.services.implementations

import com.brayan.renovar.api.request.ADDEPISEmployee
import com.brayan.renovar.api.request.AddToolsForEmployeeRequest
import com.brayan.renovar.api.request.EmployeeUpdateRequest
import com.brayan.renovar.api.request.FunctionRequest
import com.brayan.renovar.api.request.RemoveToolEmployeeRequest
import com.brayan.renovar.api.request.ReturnEpi
import com.brayan.renovar.api.response.EmployeeResponse
import com.brayan.renovar.database.entities.EmployeeEPIId
import com.brayan.renovar.database.repositories.interfaces.CreationRepository
import com.brayan.renovar.database.repositories.interfaces.EPIRepository
import com.brayan.renovar.database.repositories.interfaces.EmployeeEPIRepository
import com.brayan.renovar.database.repositories.interfaces.EmployeeRepository
import com.brayan.renovar.database.repositories.interfaces.ToolRepository
import com.brayan.renovar.database.repositories.interfaces.ToolsEmployeeRepository
import com.brayan.renovar.enum.EPIStatus
import com.brayan.renovar.enum.EmployeeStatus
import com.brayan.renovar.models.EmployeeEPIModel
import com.brayan.renovar.models.EmployeeModel
import com.brayan.renovar.models.FunctionModel
import com.brayan.renovar.models.ToolsEmployeesModel
import com.brayan.renovar.services.interfaces.EmployeeService
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class EmployeeServiceImpl(
    val employeeRepository: EmployeeRepository,
    val epiRepository: EPIRepository,
    val toolRepository: ToolRepository,
    private val entityManager: EntityManager,
    val employeeEPIRepository: EmployeeEPIRepository,
    val creationRepository: CreationRepository,
    val toolsEmployeeRepository: ToolsEmployeeRepository


    ):EmployeeService{
    override fun createFunction(functionRequest: FunctionRequest): FunctionModel {
        val functionModel = FunctionModel(
            function = functionRequest.name,
            description = functionRequest.description
        )
        return employeeRepository.save(functionModel)
    }

    override fun getFunctionById(id: UUID): FunctionModel {
        return employeeRepository.findById(id)
    }

    override fun getFunctionByName(name: String): List<FunctionModel> {
        return employeeRepository.findByName(name)
    }

    override fun getFunctions(): List<FunctionModel> {
        return employeeRepository.findAll()
    }

    override fun createEmployee(employeeModel: EmployeeModel): EmployeeResponse {
        val query = entityManager.createNativeQuery("SELECT nextval('registration_sequence')")
        val nextRegistration = (query.singleResult as Number).toInt()
        val employee = employeeModel.copy(
            registration = nextRegistration,
            employeeStatus = EmployeeStatus.ATIVO
        )
        return employeeRepository.saveEmployee(employee)
    }

    override fun generateRegistration(): Int {
        return employeeRepository.findLastRegistration() + 1
    }

    override fun getEmployees(): List<EmployeeResponse> {
        return employeeRepository.findAllEmployees()
    }

    override fun getEmployeeById(employeeId: UUID): EmployeeResponse {
        return employeeRepository.findEmployeeById(employeeId)
    }


    override fun updateEmployee(employeeRequest: EmployeeUpdateRequest): EmployeeResponse {
        val employeeModel = employeeRepository.findEmployeeByIdModel(employeeRequest.id)
        val employee = employeeModel.copy(
            name = employeeRequest.name?: employeeModel.name,
            functionModel = employeeRequest.functionModel?: employeeModel.functionModel,
            phones = employeeRequest.phones?: employeeModel.phones,
            hourlyRate = employeeRequest.hourlyRate?: employeeModel.hourlyRate,
            employeeStatus = employeeRequest.status?: employeeModel.employeeStatus
        )
        return employeeRepository.saveEmployee(employee)
    }

    override fun getEmployeesByStatus(status: EmployeeStatus): List<EmployeeResponse> {
        return employeeRepository.findAllByStatus(status)
    }

    override fun getEmployeesByName(name: String): List<EmployeeResponse> {
        return employeeRepository.findAllByName(name)
    }

    override fun findAllIds(ids:List<UUID>): List<EmployeeResponse> {
        return employeeRepository.findAllById(ids).map { it.toEmployeeResponse() }
    }



    override fun returnEPI(returnEpi: ReturnEpi): EmployeeModel {
        val employeeModel = getEmployeeById(returnEpi.employeeId)
        val epiList = employeeModel.employeeEpis.toMutableList()
        returnEpi.episQuantidade.forEach { epiMap ->
            epiMap.forEach { (epiId, quantity) ->
                //verifica se o epi ja esta na lista de epis do funcionario e verifica se o epi já foi devolvido
                if (epiList.none { it.epiId == epiId && it.returnDate == null }) {
                    throw Exception("Funcionario não possui esse EPI para devolver")
                }
                val employeeEpiModel = epiList.find { it.epiId == epiId && it.returnDate == null } ?: throw Exception("EPI não encontrado")
                val newEmployeeEpiModel = employeeEpiModel.copy(
                    returnDate = returnEpi.returnDate,
                    epiStatus = returnEpi.epiStatus
                )
                employeeEPIRepository.save(newEmployeeEpiModel)
                if (returnEpi.returnToStock) {
                    val epi = epiRepository.findById(epiId)
                    val newEpi = epi.copy(
                        quantity = epi.quantity + quantity.toInt()
                    )
                    epiRepository.save(newEpi)
                }

            }
        }
        return employeeRepository.findEmployeeById(returnEpi.employeeId)
    }



    override fun addToolsForEmployee(addToolsForEmployeeRequest: AddToolsForEmployeeRequest): EmployeeModel {
        val employeeModel = getEmployeeById(addToolsForEmployeeRequest.employeeId)
        val toolsList = employeeModel.toolsEmployee.toMutableList()
        addToolsForEmployeeRequest.tools.forEach { toolMap ->
            toolMap.forEach { (toolId, quantity) ->
                if (toolsList.any { it.toolId == toolId && it.endDate == null }) {
                    throw Exception("Funcionario já possui essa ferramenta")
                }
                val toolsEmployeeModel = ToolsEmployeesModel(
                    employeeId = employeeModel.id ?: throw Exception("ID do funcionário não encontrado"),
                    toolId = toolId,
                    startDate = addToolsForEmployeeRequest.startDate,
                    quantity = quantity.toInt(),
                    status = addToolsForEmployeeRequest.status,
                    creationDateId = creationRepository.saveCreation() ?: throw Exception("Erro ao salvar data de criação"),
                )
                toolsEmployeeRepository.save(toolsEmployeeModel)

                val newTool = tool.copy(
                    quantity = tool.quantity - quantity.toInt()
                )
                toolRepository.save(newTool)
            }
        }
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
                status = removeToolsFroEmployee.status,
                creationDate = employee.toolsEmployee.find { it.toolId == it.toolId }?.creationDate ?: throw Exception("Tool not found"),
            ))
            val newTool = tool.copy(
                quantity = tool.quantity + it.value.toInt()
            )
            toolRepository.save(newTool)
        }
        val employeeModel = employee.copy(
            toolsEmployee = toolsList
        )
        return employeeRepository.saveEmployee(employeeModel)
    }



    override fun getEmployeeByRegistration(registration: Int): EmployeeResponse {
        return employeeRepository.findEmployeeByRegistration(registration)
    }

    override fun getEmployeeByCPF(cpf: String): EmployeeResponse {
        return employeeRepository.findEmployeeByCPF(cpf)
    }

    override fun getEmployeeModel(id: UUID): EmployeeModel {
        return employeeRepository.findEmployeeByIdModel(id)
    }

}