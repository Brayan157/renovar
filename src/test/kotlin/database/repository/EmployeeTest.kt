package database.repository

import com.brayan.renovar.api.request.EmployeeUpdateRequest
import com.brayan.renovar.api.request.FunctionRequest
import com.brayan.renovar.api.response.EmployeeResponse
import com.brayan.renovar.database.repositories.interfaces.EmployeeRepository
import com.brayan.renovar.enum.EmployeeStatus
import com.brayan.renovar.models.AddressModel
import com.brayan.renovar.models.EmployeeModel
import com.brayan.renovar.models.FunctionModel
import com.brayan.renovar.services.implementations.EmployeeServiceImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import java.time.LocalDate
import java.util.UUID

class EmployeeTest {
    private val employeeRepository: EmployeeRepository = mockk()
    private val entityManager: EntityManager = mockk()
    private val employeeService = EmployeeServiceImpl(employeeRepository, entityManager)

    private lateinit var functionRequest: FunctionRequest
    private lateinit var functionModel: FunctionModel
    private lateinit var employeeModel: EmployeeModel
    private lateinit var employeeResponse: EmployeeResponse
    private lateinit var employeeUpdateRequest: EmployeeUpdateRequest
    private lateinit var id: UUID

    @BeforeEach
    fun setUp() {
        id = UUID.randomUUID()
        functionRequest = FunctionRequest("Developer", "Desenvolve software")
        functionModel = FunctionModel(id, "Developer", "Desenvolve software")
        employeeModel = EmployeeModel(
            id = id,
            name = "João Silva",
            cpf = "12345678901",
            birthDate = LocalDate.now(),
            functionModel = functionModel,
            phones = "11987654321",
            addressModel = AddressModel(
                id = UUID.randomUUID(),
                street = "Rua A",
                number = "123",
                neighborhood = "Bairro X",
                city = "Cidade Y",
                state = "Estado Z",
                zipCode = "12345-678",
                complement = "Apto 101"
            ),
            generalRegistration = "GR123",
            hourlyRate = 50.0,
            employeeStatus = EmployeeStatus.ATIVO
        )
        employeeResponse = EmployeeResponse(
            id = id,
            name = "João Silva",
            registration = 1,
            cpf = "12345678901",
            birthDate = "1990-01-01",
            phones = "11987654321",
            generalRegistration = "GR123",
            hourlyRate = 50.0,
            function = "Developer",
            status = EmployeeStatus.ATIVO,
            addressModel = AddressModel(
                id = UUID.randomUUID(),
                street = "Rua A",
                number = "123",
                neighborhood = "Bairro X",
                city = "Cidade Y",
                state = "Estado Z",
                zipCode = "12345-678",
                complement = "Apto 101"
            )
        )
        employeeUpdateRequest = EmployeeUpdateRequest(
            id = id,
            name = "João Silva Atualizado",
            hourlyRate = 60.0
        )
    }

    @Test
    fun `deve criar função com sucesso`() {
        every { employeeRepository.save(any()) } returns functionModel

        val result = employeeService.createFunction(functionRequest)

        assertNotNull(result)
        assertEquals(functionModel, result)
        verify(exactly = 1) { employeeRepository.save(any()) }
    }

    @Test
    fun `deve obter função pelo ID com sucesso`() {
        every { employeeRepository.findById(id) } returns functionModel

        val result = employeeService.getFunctionById(id)

        assertNotNull(result)
        assertEquals(functionModel, result)
        verify(exactly = 1) { employeeRepository.findById(id) }
    }

    @Test
    fun `deve obter função pelo nome com sucesso`() {
        every { employeeRepository.findByName("Developer") } returns listOf(functionModel)

        val result = employeeService.getFunctionByName("Developer")

        assertNotNull(result)
        assertEquals(listOf(functionModel), result)
        verify(exactly = 1) { employeeRepository.findByName("Developer") }
    }

    @Test
    fun `deve obter todas as funções com sucesso`() {
        every { employeeRepository.findAll() } returns listOf(functionModel)

        val result = employeeService.getFunctions()

        assertNotNull(result)
        assertEquals(listOf(functionModel), result)
        verify(exactly = 1) { employeeRepository.findAll() }
    }

    @Test
    fun `deve criar funcionário com sucesso`() {
        every { employeeRepository.saveEmployee(any()) } returns employeeResponse
        every { entityManager.createNativeQuery(any<String>()) } returns mockk {
            every { singleResult } returns 1
        }

        val result = employeeService.createEmployee(employeeModel)

        assertNotNull(result)
        assertEquals(employeeResponse, result)
        verify(exactly = 1) { employeeRepository.saveEmployee(any()) }
    }

    @Test
    fun `deve obter todos os funcionários com sucesso`() {
        every { employeeRepository.findAllEmployees() } returns listOf(employeeResponse)

        val result = employeeService.getEmployees()

        assertNotNull(result)
        assertEquals(listOf(employeeResponse), result)
        verify(exactly = 1) { employeeRepository.findAllEmployees() }
    }

    @Test
    fun `deve obter funcionário pelo ID com sucesso`() {
        every { employeeRepository.findEmployeeById(id) } returns employeeResponse

        val result = employeeService.getEmployeeById(id)

        assertNotNull(result)
        assertEquals(employeeResponse, result)
        verify(exactly = 1) { employeeRepository.findEmployeeById(id) }
    }

    @Test
    fun `deve atualizar funcionário com sucesso`() {
        every { employeeRepository.findEmployeeByIdModel(id) } returns employeeModel
        every { employeeRepository.saveEmployee(any()) } returns employeeResponse.copy(name = "João Silva Atualizado")

        val result = employeeService.updateEmployee(employeeUpdateRequest)

        assertNotNull(result)
        assertEquals("João Silva Atualizado", result.name)
        verify(exactly = 1) { employeeRepository.findEmployeeByIdModel(id) }
        verify(exactly = 1) { employeeRepository.saveEmployee(any()) }
    }

    @Test
    fun `deve obter funcionários pelo status com sucesso`() {
        every { employeeRepository.findAllByStatus(EmployeeStatus.ATIVO) } returns listOf(employeeResponse)

        val result = employeeService.getEmployeesByStatus(EmployeeStatus.ATIVO)

        assertNotNull(result)
        assertEquals(listOf(employeeResponse), result)
        verify(exactly = 1) { employeeRepository.findAllByStatus(EmployeeStatus.ATIVO) }
    }

    @Test
    fun `deve obter funcionários pelo nome com sucesso`() {
        every { employeeRepository.findAllByName("João Silva") } returns listOf(employeeResponse)

        val result = employeeService.getEmployeesByName("João Silva")

        assertNotNull(result)
        assertEquals(listOf(employeeResponse), result)
        verify(exactly = 1) { employeeRepository.findAllByName("João Silva") }
    }
}