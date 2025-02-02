package com.brayan.renovar

import com.brayan.renovar.api.request.EmployeeUpdateRequest
import com.brayan.renovar.api.request.FunctionRequest
import com.brayan.renovar.database.entities.Employee
import com.brayan.renovar.database.repositories.interfaces.EmployeeRepository
import com.brayan.renovar.database.repositories.springData.EmployeeSpringDataRepository
import com.brayan.renovar.enum.EmployeeStatus
import com.brayan.renovar.models.AddressModel
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
    private val employeeServiceImpl = EmployeeServiceImpl(EmployeeRepository = employeeRepository)
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
        val functionModel = FunctionModel(id = functionId, function = "Developer", description = "Software development")

        every { employeeRepository.findById(functionId) } returns functionModel

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
        val functionModel = FunctionModel(function = "Developer", description = "Software development")
        val functionList = listOf(functionModel)

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

}