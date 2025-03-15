package database.repository

import com.brayan.renovar.api.request.EmployeeToolRequest
import com.brayan.renovar.api.request.EmployeeToolUpdateRequest
import com.brayan.renovar.api.response.EmployeeResponse
import com.brayan.renovar.database.entities.ToolsEmployees
import com.brayan.renovar.database.entities.ToolsEmployeesKey
import com.brayan.renovar.database.repositories.interfaces.CreationRepository
import com.brayan.renovar.database.repositories.interfaces.EmployeeRepository
import com.brayan.renovar.database.repositories.interfaces.EmployeeToolRepository
import com.brayan.renovar.database.repositories.interfaces.ToolRepository
import com.brayan.renovar.enum.EmployeeStatus
import com.brayan.renovar.enum.ToolEmployee
import com.brayan.renovar.enum.ToolStatus
import com.brayan.renovar.models.AddressModel
import com.brayan.renovar.models.ToolModel
import com.brayan.renovar.models.ToolsEmployeesModel
import com.brayan.renovar.services.implementations.EmployeeToolServiceImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.verify
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

class EmployeeTool {

    private val employeeToolRepository: EmployeeToolRepository = mockk()
    private val creationRepository: CreationRepository = mockk()
    private val employeeRepository: EmployeeRepository = mockk()
    private val toolRepository: ToolRepository = mockk()
    private val service = EmployeeToolServiceImpl(employeeToolRepository, creationRepository, employeeRepository, toolRepository)

    private lateinit var employeeToolRequest: EmployeeToolRequest
    private lateinit var employeeToolUpdateRequest: EmployeeToolUpdateRequest
    private lateinit var employeeToolModel: ToolsEmployeesModel
    private lateinit var employeeResponse: EmployeeResponse
    private lateinit var toolModel: ToolModel
    private lateinit var toolId: UUID
    private lateinit var employeeId: UUID

    @BeforeEach
    fun setUp() {
        employeeId = UUID.randomUUID()
        toolId = UUID.randomUUID()
        employeeToolRequest = EmployeeToolRequest(employeeId, toolId, LocalDate.now(), 1)
        employeeToolUpdateRequest = EmployeeToolUpdateRequest(ToolsEmployeesKey(employeeId, toolId), LocalDate.now())
        employeeResponse = EmployeeResponse(
            id = employeeId,
            name = "João",
            registration = 12345,
            cpf = "123.456.789-00",
            birthDate = LocalDate.of(1990, 1, 1),
            phones = "(62) 99999-9999",
            generalRegistration = "MG-12.345.678",
            hourlyRate = 20.0,
            function = "Soldador",
            status = EmployeeStatus.ATIVO,
            addressModel = AddressModel(employeeId, "Rua A", "123", "Bairro X", "Cidade Y", "Estado Z", "12345-678", "Apto 101")
        )
        toolModel = ToolModel(
            id = toolId,
            name = "Furadeira",
            purchaseDate = LocalDate.now(),
            unitValue = 250.0,
            creationDate = LocalDateTime.now(),
            updateDate = LocalDateTime.now(),
            toolStatus = ToolStatus.PARADA,
            quantity = 1
        )
        employeeToolModel = ToolsEmployeesModel(
            employeeId = employeeId,
            toolId = toolId,
            startDate = LocalDate.now(),
            endDate = null,
            quantity = 1,
            creationDate = LocalDateTime.now(),
            updateDate = LocalDateTime.now(),
            status = ToolEmployee.ENTREGUE,
            creationDateId = UUID.randomUUID()
        )
    }

    @Test
    fun `deve salvar um novo empréstimo com sucesso`() {
        every { employeeToolRepository.findByToolIdAndEndDateIsNull(toolId) } returns emptyList()
        every { employeeRepository.findEmployeeById(employeeId) } returns employeeResponse
        every { toolRepository.findById(toolId) } returns toolModel
        every { creationRepository.saveCreation() } returns UUID.randomUUID()
        every { employeeToolRepository.save(any()) } returns employeeToolModel
        every { toolRepository.save(any()) } returns toolModel.copy(toolStatus = ToolStatus.TRABALHANDO)

        val result = service.save(employeeToolRequest)

        assertNotNull(result)
        assertEquals(employeeId, result.employee.id)
        assertEquals(toolId, result.tool.id)
        verify(exactly = 1) { employeeToolRepository.save(any()) }
        verify(exactly = 1) { toolRepository.save(any()) }
    }

    @Test
    fun `deve lançar exceção quando a ferramenta já está emprestada`() {
        every { employeeToolRepository.findByToolIdAndEndDateIsNull(toolId) } returns listOf(employeeToolModel)

        val exception = assertThrows<Exception> { service.save(employeeToolRequest) }
        assertEquals("Ferramenta já está emprestada", exception.message)
        verify(exactly = 1) { employeeToolRepository.findByToolIdAndEndDateIsNull(toolId) }
    }

    @Test
    fun `deve atualizar um empréstimo com sucesso`() {
        every { employeeRepository.findEmployeeById(employeeId) } returns employeeResponse
        every { toolRepository.findById(toolId) } returns toolModel.copy(toolStatus = ToolStatus.TRABALHANDO)
        every { employeeToolRepository.findById(employeeToolUpdateRequest.id) } returns employeeToolModel
        every { employeeToolRepository.save(any()) } returns employeeToolModel.copy(endDate = LocalDate.now(), status = ToolEmployee.DEVOLVIDO)
        every { toolRepository.save(any()) } returns toolModel.copy(toolStatus = ToolStatus.PARADA)

        val result = service.update(employeeToolUpdateRequest)

        assertNotNull(result)
        assertEquals(ToolEmployee.DEVOLVIDO, result.status)
        verify(exactly = 1) { employeeRepository.findEmployeeById(employeeId) }
        verify(exactly = 1) { employeeToolRepository.findById(employeeToolUpdateRequest.id) }
        verify(exactly = 1) { employeeToolRepository.save(any()) }
        verify(exactly = 1) { toolRepository.save(any()) }
    }


    @Test
    fun `deve obter lista de todos os empréstimos`() {
        every { employeeToolRepository.findAll() } returns listOf(employeeToolModel)
        every { toolRepository.findById(toolId) } returns toolModel
        every { employeeRepository.findEmployeeById(employeeId) } returns employeeResponse

        val result = service.listAll()

        assertNotNull(result)
        assertEquals(1, result.size)
        verify(exactly = 1) { employeeToolRepository.findAll() }
    }
    
}