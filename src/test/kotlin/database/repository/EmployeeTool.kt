package database.repository

import com.brayan.renovar.api.request.EmployeeToolRequest
import com.brayan.renovar.api.request.EmployeeToolUpdateRequest
import com.brayan.renovar.database.entities.ToolsEmployees
import com.brayan.renovar.database.entities.ToolsEmployeesKey
import com.brayan.renovar.database.repositories.interfaces.CreationRepository
import com.brayan.renovar.database.repositories.interfaces.EmployeeRepository
import com.brayan.renovar.database.repositories.interfaces.EmployeeToolRepository
import com.brayan.renovar.database.repositories.interfaces.ToolRepository
import com.brayan.renovar.enum.EmployeeStatus
import com.brayan.renovar.enum.ToolEmployee
import com.brayan.renovar.enum.ToolStatus
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
import java.util.UUID

class EmployeeTool {
    private val employeeToolRepository: EmployeeToolRepository = mockk()
    private val creationRepository: CreationRepository = mockk()
    private val employeeRepository: EmployeeRepository = mockk()
    private val toolRepository: ToolRepository = mockk()
    private val employeeToolService = EmployeeToolServiceImpl(
        employeeToolRepository, creationRepository, employeeRepository, toolRepository
    )

    private lateinit var employeeToolRequest: EmployeeToolRequest
    private lateinit var employeeToolUpdateRequest: EmployeeToolUpdateRequest
    private lateinit var employeeId: UUID
    private lateinit var toolId: UUID

    @BeforeEach
    fun setup() {
        employeeId = UUID.randomUUID()
        toolId = UUID.randomUUID()
        employeeToolRequest = EmployeeToolRequest(
            employeeId = employeeId,
            toolId = toolId,
            startDate = LocalDate.now(),
            quantity = 1
        )
        employeeToolUpdateRequest = EmployeeToolUpdateRequest(
            id = ToolsEmployeesKey(employeeId, toolId),
            endDate = LocalDate.now(),
            status = ToolEmployee.DEVOLVIDO
        )
    }

    @Test
    fun `deve lançar exceção quando ferramenta já estiver emprestada`() {
        every { employeeToolRepository.findByToolIdAndEndDateIsNull(toolId) } returns listOf(mockk<ToolsEmployeesModel>())

        val exception = assertThrows<Exception> { employeeToolService.save(employeeToolRequest) }
        assertEquals("Ferramenta já está emprestada", exception.message)
    }

    @Test
    fun `deve lançar exceção quando funcionário não estiver trabalhando`() {
        every { employeeToolRepository.findByToolIdAndEndDateIsNull(toolId) } returns emptyList()
        every { employeeRepository.findEmployeeById(employeeId) } returns mockk { every { status } returns EmployeeStatus.INATIVO }

        val exception = assertThrows<Exception> { employeeToolService.save(employeeToolRequest) }
        assertEquals("Funcionário não está trabalhando", exception.message)
    }

    @Test
    fun `deve lançar exceção quando ferramenta não está disponível`() {
        every { employeeToolRepository.findByToolIdAndEndDateIsNull(toolId) } returns emptyList()
        every { employeeRepository.findEmployeeById(employeeId) } returns mockk { every { status } returns EmployeeStatus.ATIVO }
        every { toolRepository.findById(toolId) } returns mockk { every { toolStatus } returns ToolStatus.TRABALHANDO }

        val exception = assertThrows<Exception> { employeeToolService.save(employeeToolRequest) }
        assertEquals("Ferramenta não está disponível", exception.message)
    }

    @Test
    fun `deve emprestar ferramenta com sucesso`() {
        every { employeeToolRepository.findByToolIdAndEndDateIsNull(toolId) } returns emptyList()
        every { employeeRepository.findEmployeeById(employeeId) } returns mockk { every { status } returns EmployeeStatus.ATIVO }
        every { toolRepository.findById(toolId) } returns mockk { every { toolStatus } returns ToolStatus.PARADA }
        every { creationRepository.saveCreation() } returns UUID.randomUUID()
        every { employeeToolRepository.save(any()) } returns mockk<ToolsEmployees> { every { toResponse() } returns mockk() }
        every { toolRepository.save(any()) } returns mockk<ToolModel>()

        val result = employeeToolService.save(employeeToolRequest)

        assertNotNull(result)
        verify(exactly = 1) { employeeToolRepository.save(any()) }
        verify(exactly = 1) { toolRepository.save(any()) }
    }

    @Test
    fun `deve devolver ferramenta com sucesso`() {
        every { toolRepository.findById(toolId) } returns mockk { every { toolStatus } returns ToolStatus.TRABALHANDO }
        every { toolRepository.save(any()) } returns mockk<ToolModel>()
        every { employeeToolRepository.findById(any()) } returns mockk<ToolsEmployees> { every { toResponse() } returns mockk() }
        every { employeeToolRepository.save(any()) } returns mockk<ToolsEmployees>()

        val result = employeeToolService.update(employeeToolUpdateRequest)

        assertNotNull(result)
        verify(exactly = 1) { toolRepository.save(any()) }
        verify(exactly = 1) { employeeToolRepository.save(any()) }
    }

    @Test
    fun `deve listar todos os registros`() {
        every { employeeToolRepository.findAll() } returns listOf(mockk<ToolsEmployees> { every { toResponse() } returns mockk() })

        val result = employeeToolService.listAll()

        assertNotNull(result)
        assertTrue(result.isNotEmpty())
        verify(exactly = 1) { employeeToolRepository.findAll() }
    }
}