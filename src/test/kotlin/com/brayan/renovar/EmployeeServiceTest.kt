package com.brayan.renovar

import com.brayan.renovar.api.request.ADDEPISEmployee
import com.brayan.renovar.api.request.AddToolsForEmployeeRequest
import com.brayan.renovar.api.request.EmployeeUpdateRequest
import com.brayan.renovar.api.request.FunctionRequest
import com.brayan.renovar.api.request.RemoveToolEmployeeRequest
import com.brayan.renovar.api.request.ReturnEpi
import com.brayan.renovar.database.entities.Address
import com.brayan.renovar.database.entities.EPI
import com.brayan.renovar.database.entities.Employee
import com.brayan.renovar.database.entities.EmployeeEPI
import com.brayan.renovar.database.entities.EmployeeEPIId
import com.brayan.renovar.database.entities.Function
import com.brayan.renovar.database.entities.Tool
import com.brayan.renovar.database.entities.ToolsEmployees
import com.brayan.renovar.database.entities.ToolsEmployeesKey
import com.brayan.renovar.database.entities.Work
import com.brayan.renovar.database.repositories.interfaces.EPIRepository
import com.brayan.renovar.database.repositories.interfaces.EmployeeRepository
import com.brayan.renovar.database.repositories.interfaces.ToolRepository
import com.brayan.renovar.database.repositories.interfaces.WorkRepository
import com.brayan.renovar.database.repositories.springData.EmployeeSpringDataRepository
import com.brayan.renovar.enum.EPIStatus
import com.brayan.renovar.enum.EmployeeStatus
import com.brayan.renovar.enum.ToolEmployee
import com.brayan.renovar.enum.ToolStatus
import com.brayan.renovar.models.AddressModel
import com.brayan.renovar.models.EmployeeEPIModel
import com.brayan.renovar.models.EmployeeModel
import com.brayan.renovar.models.FunctionModel
import com.brayan.renovar.services.implementations.EmployeeServiceImpl
import com.brayan.renovar.services.interfaces.EmployeeService
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

class EmployeeServiceTest {
    private val employeeRepository = mockk<EmployeeRepository>()
    private val epiRepository = mockk<EPIRepository>()
    private val toolRepository = mockk<ToolRepository>()
    private val workRepository = mockk<WorkRepository>()
    private val employeeServiceImpl = EmployeeServiceImpl(employeeRepository, epiRepository, toolRepository)
    private val functionModel = FunctionModel(
        id = UUID.randomUUID(),
        function = "function 2",
        description = "description test"
    )
    private val addressModel = AddressModel(
        id = UUID.randomUUID(),
        street = "street",
        city = "city",
        number = "123",
        neighborhood = "neighborhood",
        state = "state",
        zipCode = "12345",
        complement = "complement"
    )
    private val employeeId = UUID.randomUUID()
    private val employeeModel = EmployeeModel(
        id = employeeId,
        name = "João Silva",
        cpf = "12345678909",
        birthDate = LocalDate.of(1990, 5, 10),
        functionModel = functionModel,
        creationDate = LocalDateTime.now(),
        updateDate = LocalDateTime.now(),
        phones = "11987654321",
        addressModel = addressModel,
        generalRegistration = "GR123",
        hourlyRate = 30.0,
        employeeStatus = EmployeeStatus.ATIVO,
        employeesWorks = emptyList(),
        employeeEpis = emptyList(),
        toolsEmployee = emptyList()
    )
    val employeeModel2 = EmployeeModel(
        id = UUID.randomUUID(),
        name = "Jane Doe",
        cpf = "98765432101",
        birthDate = LocalDate.of(1995, 5, 15),
        functionModel = functionModel,
        creationDate = LocalDateTime.now(),
        updateDate = LocalDateTime.now(),
        phones = "987654321",
        addressModel = addressModel,
        generalRegistration = "GR456",
        hourlyRate = 60.0,
        employeeStatus = EmployeeStatus.INATIVO,
        employeesWorks = emptyList(),
        employeeEpis = emptyList(),
        toolsEmployee = emptyList()
    )
    val epis: List<EPI> = emptyList()
    val tools: List<Tool> = emptyList()
    val works: List<Work> = emptyList()
    val epiId = UUID.randomUUID()
    val addEPIRequest = ADDEPISEmployee(
        episQuantidade = mapOf(epiId to 2),
        employeeId = employeeId,
        deliveryDate = LocalDate.now(),
        reason = "Safety reasons"
    )
    val epi = EPI(
        id = epiId,
        name = "Helmet",
        approvalCertificate = "123",
        unitValue = 50.0,
        manufacturingDate = LocalDate.of(2021, 1, 1),
        expirationDate = LocalDate.of(2029, 1, 1),
        tag = "TAG123",
        lot = "LOT123",
        employeeEpis = emptyList(),
        quantity = 10
    )
    val returnEPIRequest = ReturnEpi(
        episQuantidade = mapOf(epiId to 2L),
        employeeId = employeeId,
        returnDate = LocalDate.now()
    )
    val toolId = UUID.randomUUID()
    val tool = Tool(
        id = toolId,
        name = "Hammer",
        purchaseDate = LocalDate.of(2021, 1, 1),
        toolStatus = ToolStatus.PARADA,
        unitValue = 20.0,
        toolsWorks = emptyList(),
        toolsEmployees = emptyList(),
        quantity = 10
    )
    @Test
    fun `saveEmployee should handle empty lists`() {
        // Arrange: Cria mocks dos modelos necessários

        // Cria um modelo de funcionário com listas vazias


        val savedEmployee = slot<EmployeeModel>()


        every { employeeRepository.saveEmployee(capture(savedEmployee)) } answers { savedEmployee.captured }
        every { employeeRepository.findLastRegistration() } returns "001" // Simula registro gerado

        // Act: Salva o funcionário
        val result = employeeServiceImpl.createEmployee(employeeModel)

        // Assert: Verifica se o funcionário foi salvo corretamente com as listas vazias
        assertNotNull(result)
        assertEquals(employeeModel.id, savedEmployee.captured.id)
        assertEquals("001", savedEmployee.captured.registration) // Confere o registro gerado
        assertTrue(savedEmployee.captured.employeesWorks.isEmpty())
        assertTrue(savedEmployee.captured.employeeEpis.isEmpty())
        assertTrue(savedEmployee.captured.toolsEmployee.isEmpty())
    }

    @Test
    fun `of should map EmployeeModel to Employee with empty lists`() {
        // Arrange: Cria um EmployeeModel com listas vazias


        // Act: Converte o modelo para a entidade
        val employee = Employee.of(employeeModel2, emptyList(), emptyList(), emptyList())

        // Assert: Verifica se os atributos foram mapeados corretamente
        assertEquals(employeeModel2.id, employee.id)
        assertEquals(employeeModel2.name, employee.name)
        assertEquals(employeeModel2.cpf, employee.cpf)
        assertEquals(employeeModel2.birthDate, employee.birthDate)
        assertTrue(employee.employeesWorks.isEmpty())
        assertTrue(employee.employeeEpis.isEmpty())
        assertTrue(employee.toolsEmployee.isEmpty())
    }

    @Test
    fun `getEmployees should return a list of employees`() {
        // Arrange: Define o comportamento do repositório mockado
        every { employeeRepository.findAllEmployees() } returns listOf(employeeModel)

        // Act: Chama a função de serviço
        val result = employeeServiceImpl.getEmployees()

        // Assert: Verifica se o resultado corresponde ao esperado
        assertEquals(1, result.size)
        assertEquals("João Silva", result[0].name)
        assertEquals(EmployeeStatus.ATIVO, result[0].employeeStatus)
    }

    @Test
    fun `getEmployeeById should return employee when found`() {
        // Arrange
        every { employeeRepository.findEmployeeById(employeeId) } returns employeeModel

        // Act
        val result = employeeServiceImpl.getEmployeeById(employeeId)

        // Assert
        assertNotNull(result)
        assertEquals(employeeId, result.id)
        assertEquals("João Silva", result.name)
        assertEquals(EmployeeStatus.ATIVO, result.employeeStatus)

        verify(exactly = 1) { employeeRepository.findEmployeeById(employeeId) }
    }

    @Test
    fun `createFunction should save and return a FunctionModel`() {
        // Arrange
        val functionRequest = FunctionRequest(name = "Developer", description = "Software development")
        val functionModel = FunctionModel(function = "Developer", description = "Software development")
        val savedFunctionModel = functionModel.copy(id = UUID.randomUUID())

        every { employeeRepository.save(functionModel) } returns savedFunctionModel

        // Act
        val result = employeeServiceImpl.createFunction(functionRequest)

        // Assert
        assertNotNull(result)
        assertEquals("Developer", result.function)
        assertEquals("Software development", result.description)
        verify(exactly = 1) { employeeRepository.save(functionModel) }
    }

    @Test
    fun `getFunctionById should return a FunctionModel when found`() {
        // Arrange
        val functionId = UUID.randomUUID()
        val functionModelTest =
            FunctionModel(id = functionId, function = "Developer", description = "Software development")

        every { employeeRepository.findById(functionId) } returns functionModelTest

        // Act
        val result = employeeServiceImpl.getFunctionById(functionId)

        // Assert
        assertNotNull(result)
        assertEquals(functionId, result.id)
        assertEquals("Developer", result.function)
        verify(exactly = 1) { employeeRepository.findById(functionId) }
    }

    @Test
    fun `getFunctionById should throw exception when function not found`() {
        // Arrange
        val functionId = UUID.randomUUID()

        every { employeeRepository.findById(functionId) } throws NoSuchElementException("Function not found")

        // Act & Assert
        assertThrows<NoSuchElementException> {
            employeeServiceImpl.getFunctionById(functionId)
        }
        verify(exactly = 1) { employeeRepository.findById(functionId) }
    }

    @Test
    fun `getFunctionByName should return a list of FunctionModel`() {
        // Arrange
        val functionName = "Developer"
        val functionModelTest = FunctionModel(function = "Developer", description = "Software development")
        val functionList = listOf(functionModelTest)

        every { employeeRepository.findByName(functionName) } returns functionList

        // Act
        val result = employeeServiceImpl.getFunctionByName(functionName)

        // Assert
        assertEquals(1, result.size)
        assertEquals("Developer", result[0].function)
        verify(exactly = 1) { employeeRepository.findByName(functionName) }
    }

    @Test
    fun `getFunctions should return a list of FunctionModel`() {
        // Arrange
        val functionModel1 = FunctionModel(function = "Developer", description = "Software development")
        val functionModel2 = FunctionModel(function = "Manager", description = "Team management")
        val functionList = listOf(functionModel1, functionModel2)

        every { employeeRepository.findAll() } returns functionList

        // Act
        val result = employeeServiceImpl.getFunctions()

        // Assert
        assertEquals(2, result.size)
        assertEquals("Developer", result[0].function)
        assertEquals("Manager", result[1].function)
        verify(exactly = 1) { employeeRepository.findAll() }
    }

    @Test
    fun `updateEmployee should update and return the updated EmployeeModel`() {
        // Arrange
        val employeeId = UUID.randomUUID()
        val employeeUpdateRequest = EmployeeUpdateRequest(
            id = employeeId,
            name = "Updated Name",
            hourlyRate = 40.0,
            status = EmployeeStatus.INATIVO
        )
        val existingEmployeeModel = EmployeeModel(
            id = employeeId,
            name = "Original Name",
            cpf = "12345678909",
            birthDate = LocalDate.of(1990, 5, 10),
            functionModel = functionModel,
            creationDate = LocalDateTime.now(),
            updateDate = LocalDateTime.now(),
            phones = "11987654321",
            addressModel = addressModel,
            generalRegistration = "GR123",
            hourlyRate = 30.0,
            employeeStatus = EmployeeStatus.ATIVO,
            employeesWorks = emptyList(),
            employeeEpis = emptyList(),
            toolsEmployee = emptyList()
        )
        val updatedEmployeeModel = existingEmployeeModel.copy(
            name = "Updated Name",
            hourlyRate = 40.0,
            employeeStatus = EmployeeStatus.INATIVO
        )

        every { employeeRepository.findEmployeeById(employeeId) } returns existingEmployeeModel
        every { employeeRepository.saveEmployee(updatedEmployeeModel) } returns updatedEmployeeModel

        // Act
        val result = employeeServiceImpl.updateEmployee(employeeUpdateRequest)

        // Assert
        assertNotNull(result)
        assertEquals("Updated Name", result.name)
        assertEquals(40.0, result.hourlyRate)
        assertEquals(EmployeeStatus.INATIVO, result.employeeStatus)
        verify(exactly = 1) { employeeRepository.findEmployeeById(employeeId) }
        verify(exactly = 1) { employeeRepository.saveEmployee(updatedEmployeeModel) }
    }

    @Test
    fun `getEmployeesByStatus should return a list of EmployeeModel filtered by status`() {
        // Arrange
        val status = EmployeeStatus.ATIVO
        val employeeList = listOf(employeeModel)

        every { employeeRepository.findAllByStatus(status) } returns employeeList

        // Act
        val result = employeeServiceImpl.getEmployeesByStatus(status)

        // Assert
        assertEquals(1, result.size)
        assertEquals("João Silva", result[0].name)
        assertEquals(EmployeeStatus.ATIVO, result[0].employeeStatus)
        verify(exactly = 1) { employeeRepository.findAllByStatus(status) }
    }

    @Test
    fun `getEmployeeById should throw exception when employee does not exist`() {
        // Arrange
        val employeeId = UUID.randomUUID()

        // Simula o comportamento do repositório para lançar uma exceção quando o funcionário não é encontrado
        every { employeeRepository.findEmployeeById(employeeId) } throws NoSuchElementException("Empregado não encontrado")

        // Act & Assert
        val exception = assertThrows<NoSuchElementException> {
            employeeServiceImpl.getEmployeeById(employeeId)
        }

        // Verifica se a mensagem da exceção está correta
        assertEquals("Empregado não encontrado", exception.message)

        // Verifica se o método do repositório foi chamado exatamente uma vez
        verify(exactly = 1) { employeeRepository.findEmployeeById(employeeId) }
    }

    @Test
    fun `addEPI should add EPIs to employee and update EPI stock`() {
        // Arrange

        // Act
        val updatedEPI = epi.copy(quantity = epi.quantity - 2).toEPIModel()

        every { employeeRepository.findEmployeeById(employeeId) } returns employeeModel
        every { epiRepository.findById(epiId) } returns epi.toEPIModel()
        every { epiRepository.save(updatedEPI) } returns updatedEPI
        every { employeeRepository.saveEmployee(any()) } answers { firstArg() } // Aceita qualquer EmployeeModel

        // Act
        val result = employeeServiceImpl.addEPI(addEPIRequest)

        // Assert
        assertEquals(1, result.employeeEpis.size)
        assertEquals(epiId, result.employeeEpis[0].epiId)
        assertEquals(2, result.employeeEpis[0].quantity)
        verify(exactly = 1) { epiRepository.save(updatedEPI) }
    }

    @Test
    fun `addEPI should throw exception when EPI stock is insufficient`() {
        val addEPIRequest1 = ADDEPISEmployee(
            episQuantidade = mapOf(epiId to 15),
            employeeId = employeeId,
            deliveryDate = LocalDate.now(),
            reason = "Safety reasons"
        )

        val updatedEPI = epi.copy(quantity = 1).toEPIModel()

        every { employeeRepository.findEmployeeById(employeeId) } returns employeeModel
        every { epiRepository.findById(epiId) } returns epi.toEPIModel()
        every { epiRepository.save(updatedEPI) } returns updatedEPI

        // Act & Assert
        assertThrows<Exception> {
            employeeServiceImpl.addEPI(addEPIRequest1)
        }
    }
    @Test
    fun `returnEPI should return EPIs and update EPI stock`() {
        // Arrange
        val id = UUID.randomUUID()
        val employeeTestEPI = Employee(
            id = id,
            name = "João Silva",
            cpf = "12345678909",
            birthDate = LocalDate.of(1990, 5, 10),
            function = Function.of(functionModel),
            address = Address.of(addressModel),
            generalRegistration = "GR123",
            hourlyRate = 30.0,
            employeeStatus = EmployeeStatus.ATIVO,
            employeeEpis = listOf(
                EmployeeEPI(
                    id = EmployeeEPIId(employeeId, epiId),
                    employee = Employee.of(employeeModel, emptyList(), emptyList(), emptyList()),
                    epi = epi,
                    deliveryDate = LocalDate.now(),
                    quantity = 2,
                    epiStatus = EPIStatus.ENTREGUE,
                    reason = "Safety reasons"
                )
            ),
            toolsEmployee = emptyList(),
            employeesWorks = emptyList(),
            phones = "11987654321",
            registration = "001"
        )

        every { employeeRepository.findEmployeeById(employeeId) } returns employeeTestEPI.toEmployeeModel()
        every { epiRepository.findById(epiId) } returns epi.toEPIModel()
        every { employeeRepository.saveEmployee(any()) } answers { firstArg() }
        every { epiRepository.findById(epiId) } returns epi.toEPIModel()
        // Act
        val result = employeeServiceImpl.returnEPI(returnEPIRequest)

        // Assert
        assertEquals(1, result.employeeEpis.size)
        assertEquals(EPIStatus.DEVOLVIDO, result.employeeEpis[0].epiStatus)
        assertEquals(2, result.employeeEpis[0].quantity)

        verify(exactly = 1) { employeeRepository.saveEmployee(any()) }
    }
    @Test
    fun `addToolsForEmployee should add tools to employee and update tool stock`() {
        // Arrange
        val addToolsRequest = AddToolsForEmployeeRequest(
            employeeId = employeeId,
            tools = mapOf(toolId to 3L),
            startDate = LocalDate.now()
        )

        val updatedTool = tool.copy(quantity = tool.quantity?.minus(3))

        every { employeeRepository.findEmployeeById(employeeId) } returns employeeModel
        every { toolRepository.findById(toolId) } returns tool.toToolModel()
        every { toolRepository.save(updatedTool.toToolModel()) } returns updatedTool.toToolModel()
        every { employeeRepository.saveEmployee(any()) } answers { firstArg() }

        // Act
        val result = employeeServiceImpl.addToolsForEmployee(addToolsRequest)

        // Assert
        assertEquals(1, result.toolsEmployee.size)
        assertEquals(3, result.toolsEmployee[0].quantity)
        assertEquals(ToolEmployee.ENTREGUE, result.toolsEmployee[0].status)
        verify(exactly = 1) { toolRepository.save(updatedTool.toToolModel()) }

    }
    @Test
    fun `addToolsForEmployee should throw exception when tool stock is insufficient`() {
        // Arrange

        val addToolsRequest = AddToolsForEmployeeRequest(
            employeeId = employeeId,
            tools = mapOf(toolId to 15L), // Quantidade maior que o estoque
            startDate = LocalDate.now()
        )



        every { employeeRepository.findEmployeeById(employeeId) } returns employeeModel
        every { toolRepository.findById(toolId) } returns tool.toToolModel()

        // Act & Assert
        assertThrows<Exception> {
            employeeServiceImpl.addToolsForEmployee(addToolsRequest)
        }
    }
    @Test
    fun `removeToolsForEmployee should remove tools from employee and update tool stock`() {
        // Arrange

        val removeToolsRequest = RemoveToolEmployeeRequest(
            employeeId = employeeId,
            tools = mapOf(toolId to 3L),
            endDate = LocalDate.now()
        )
        val id = UUID.randomUUID()
        val employeeTool = Employee(
            id = id,
            name = "João Silva",
            cpf = "12345678909",
            birthDate = LocalDate.of(1990, 5, 10),
            function = Function.of(functionModel),
            address = Address.of(addressModel),
            generalRegistration = "GR123",
            hourlyRate = 30.0,
            employeeStatus = EmployeeStatus.ATIVO,
            toolsEmployee = listOf(
                ToolsEmployees(
                    id = ToolsEmployeesKey(employeeId, toolId),
                    employee = Employee.of(employeeModel, emptyList(), emptyList(), emptyList()),
                    tool = tool,
                    startDate = LocalDate.now(),
                    quantity = 3,
                    status = ToolEmployee.ENTREGUE
                )
            ),
            employeesWorks = emptyList(),
            employeeEpis = emptyList(),
            phones = "11987654321",
            registration = "001"
        )

        val updatedTool = tool.copy(quantity = tool.quantity?.plus(3))

        every { employeeRepository.findEmployeeById(employeeId) } returns employeeTool.toEmployeeModel()
        every { toolRepository.findById(toolId) } returns tool.toToolModel()
        every { toolRepository.save(updatedTool.toToolModel()) } returns updatedTool.toToolModel()
        every { employeeRepository.saveEmployee(any()) } answers { firstArg() }

        // Act
        val result = employeeServiceImpl.removeToolsForEmployee(removeToolsRequest)

        // Assert
        assertEquals(1, result.toolsEmployee.size)
        assertEquals(3, result.toolsEmployee[0].quantity)
        assertEquals(ToolEmployee.DEVOLVIDO, result.toolsEmployee[0].status)
        verify(exactly = 1) { toolRepository.save(updatedTool.toToolModel()) }

    }

}