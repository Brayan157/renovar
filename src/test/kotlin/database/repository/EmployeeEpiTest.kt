package database.repository

import com.brayan.renovar.api.request.AddEpiToEmployeeRequest
import com.brayan.renovar.api.request.ReturnAllEpi
import com.brayan.renovar.api.request.ReturnEpiToEmployeeRequest
import com.brayan.renovar.api.response.EmployeeEpiResponse
import com.brayan.renovar.api.response.EmployeeResponse
import com.brayan.renovar.api.response.EpiResponse
import com.brayan.renovar.database.entities.Employee

import com.brayan.renovar.database.entities.EmployeeEPI
import com.brayan.renovar.database.entities.EmployeeEPIId
import com.brayan.renovar.database.entities.Function
import com.brayan.renovar.database.repositories.interfaces.CreationRepository
import com.brayan.renovar.database.repositories.interfaces.EPIRepository
import com.brayan.renovar.database.repositories.interfaces.EmployeeEPIRepository
import com.brayan.renovar.database.repositories.interfaces.EmployeeRepository
import com.brayan.renovar.enum.EPIStatus
import com.brayan.renovar.enum.EmployeeStatus
import com.brayan.renovar.models.AddressModel
import com.brayan.renovar.models.EPIModel
import com.brayan.renovar.models.EmployeeEPIModel
import com.brayan.renovar.models.EmployeeModel
import com.brayan.renovar.models.FunctionModel
import com.brayan.renovar.services.implementations.EmployeeEpiServiceImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.time.LocalDateTime

import java.util.UUID

class EmployeeEpiServiceImplTest {

    private val employeeEPIRepository: EmployeeEPIRepository = mockk()
    private val creationRepository: CreationRepository = mockk()
    private val employeeRepository: EmployeeRepository = mockk()
    private val epiRepository: EPIRepository = mockk()
    private val employeeEpiService = EmployeeEpiServiceImpl(
        employeeEPIRepository,
        creationRepository,
        employeeRepository,
        epiRepository
    )

    private lateinit var addEpiToEmployeeRequest: AddEpiToEmployeeRequest
    private lateinit var returnEpiToEmployeeRequest: ReturnEpiToEmployeeRequest
    private lateinit var returnAllEpi: ReturnAllEpi
    private lateinit var employeeEpiResponse: EmployeeEpiResponse
    private lateinit var employeeEpiModel: EmployeeEPIModel
    private lateinit var employeeResponse: EmployeeResponse
    private lateinit var epiResponse: EpiResponse
    private lateinit var employeeEPIId: EmployeeEPIId
    private lateinit var creationDateId: UUID
    private lateinit var employeeEpiId: EmployeeEPIId
    private val employeeId = UUID.randomUUID()
    private val epiId = UUID.randomUUID()

    @BeforeEach
    fun setUp() {
        creationDateId = UUID.randomUUID()

        employeeEPIId = EmployeeEPIId(
            employeeId = employeeId,
            epiId = epiId,
            creationDateId = creationDateId
        )
        addEpiToEmployeeRequest = AddEpiToEmployeeRequest(
            employeeId = employeeId,
            epiId = epiId,
            quantity = 1,
            deliveryDate = LocalDate.now(),
            reason = "Entrega inicial"
        )
        returnEpiToEmployeeRequest = ReturnEpiToEmployeeRequest(
            employeeId = employeeId,
            epiId = epiId,
            creationDateId = creationDateId,
            quantity = 1,
            returnDate = LocalDate.now()
        )
        returnAllEpi = ReturnAllEpi(
            employeeId = employeeId,
            returnDate = LocalDate.now()
        )
        employeeResponse = EmployeeResponse(
            id = employeeId,
            name = "João Silva",
            registration = 1,
            cpf = "12345678901",
            birthDate = LocalDate.now(),
            phones = "11987654321",
            generalRegistration = "GR123",
            hourlyRate = 50.0,
            function = "Developer",
            status = EmployeeStatus.ATIVO,
            addressModel = AddressModel(
                id = UUID.randomUUID(),
                street = "Rua dos Bobos",
                number = "0",
                neighborhood = "Centro",
                city = "São Paulo",
                state = "SP",
                zipCode = "12345-678"
            )
        )
        epiResponse = EpiResponse(
            id = epiId,
            name = "Capacete de Segurança",
            approvalCertificate = "12345",
            quantity = 10,
            unitValue = 50.0,
            manufacturingDate = LocalDate.now(),
            expirationDate = LocalDate.now().plusYears(1),
            tag = "ABC123",
            lot = "LOT123"
        )
        employeeEpiResponse = EmployeeEpiResponse(
            employee = employeeResponse,
            epi = epiResponse,
            creationDateId = creationDateId,
            quantity = 1,
            deliveryDate = LocalDate.now(),
            returnDate = null,
            epiStatus = EPIStatus.ENTREGUE,
            reason = "Entrega inicial"
        )
        employeeEpiModel = EmployeeEPIModel(
            employeeId = addEpiToEmployeeRequest.employeeId,
            epiId = addEpiToEmployeeRequest.epiId,
            quantity = addEpiToEmployeeRequest.quantity,
            deliveryDate = addEpiToEmployeeRequest.deliveryDate,
            returnDate = null,
            epiStatus = EPIStatus.ENTREGUE,
            reason = addEpiToEmployeeRequest.reason,
            creationDateId = creationDateId
        )
    }

    @Test
    fun `deve adicionar EPI ao funcionário com sucesso`() {
        every { employeeEPIRepository.existByEmployeeAndEpiAndReturnDateIsNull(any(), any()) } returns emptyList()
        every { employeeRepository.findEmployeeById(any()) } returns employeeResponse
        every { creationRepository.saveCreation() } returns creationDateId
        every { employeeEPIRepository.save(any()) } returns employeeEpiResponse

        val result = employeeEpiService.addEpiToEmployee(addEpiToEmployeeRequest)

        assertNotNull(result)
        assertEquals(employeeEpiResponse, result)
        verify(exactly = 1) { employeeEPIRepository.existByEmployeeAndEpiAndReturnDateIsNull(any(), any()) }
        verify(exactly = 1) { employeeRepository.findEmployeeById(any()) }
        verify(exactly = 1) { creationRepository.saveCreation() }
        verify(exactly = 1) { employeeEPIRepository.save(any()) }
    }

    @Test
    fun `deve lançar exceção ao adicionar EPI já entregue ao funcionário`() {
        every { employeeEPIRepository.existByEmployeeAndEpiAndReturnDateIsNull(any(), any()) } returns listOf(mockk())

        val exception = assertThrows<Exception> {
            employeeEpiService.addEpiToEmployee(addEpiToEmployeeRequest)
        }

        assertEquals("Funcionário já possui esse EPI", exception.message)
        verify(exactly = 1) { employeeEPIRepository.existByEmployeeAndEpiAndReturnDateIsNull(any(), any()) }
    }

    @Test
    fun `deve lançar exceção ao adicionar EPI a funcionário inativo`() {
        every { employeeEPIRepository.existByEmployeeAndEpiAndReturnDateIsNull(any(), any()) } returns emptyList()
        every { employeeRepository.findEmployeeById(any()) } returns employeeResponse.copy(status = EmployeeStatus.INATIVO)

        val exception = assertThrows<Exception> {
            employeeEpiService.addEpiToEmployee(addEpiToEmployeeRequest)
        }

        assertEquals("Funcionário não está trabalhando", exception.message)
        verify(exactly = 1) { employeeEPIRepository.existByEmployeeAndEpiAndReturnDateIsNull(any(), any()) }
        verify(exactly = 1) { employeeRepository.findEmployeeById(any()) }
    }

    @Test
    fun `deve devolver EPI ao funcionário com sucesso`() {
        // Configuração do mock para EmployeeEPI
        val employeeEPI = mockk<EmployeeEPI> {
            // Configura o método copy
            every { copy(returnDate = any(), epiStatus = any()) } answers {
                // Simula o comportamento do método copy
                EmployeeEPI(
                    id = this@mockk.id,
                    employee = this@mockk.employee,
                    epi = this@mockk.epi,
                    creationDateEntity = this@mockk.creationDateEntity,
                    quantity = this@mockk.quantity,
                    deliveryDate = this@mockk.deliveryDate,
                    reason = this@mockk.reason,
                    returnDate = firstArg(), // Retorna o returnDate passado como argumento
                    epiStatus = secondArg(), // Retorna o epiStatus passado como argumento
                    creationDate = this@mockk.creationDate,
                    updateDate = this@mockk.updateDate
                )
            }
        }

        // Configuração do mock para o repositório
        every { employeeEPIRepository.findById(employeeEPIId) } returns employeeEpiModel
        every { employeeEPIRepository.save(any()) } returns employeeEpiResponse

        // Executa o método a ser testado
        val result = employeeEpiService.returnEpiToEmployee(returnEpiToEmployeeRequest)

        // Verificações
        assertNotNull(result)
        assertEquals(employeeEpiResponse, result)
        assertEquals(EPIStatus.DEVOLVIDO, result.epiStatus)
        assertEquals(returnEpiToEmployeeRequest.returnDate, result.returnDate)

        // Verifica se os métodos do repositório foram chamados corretamente
        verify(exactly = 1) { employeeEPIRepository.findById(employeeEPIId) }
        verify(exactly = 1) { employeeEPIRepository.save(any()) }
    }

    @Test
    fun `deve listar todos os EPIs dos funcionários com sucesso`() {
        every { employeeEPIRepository.findAll() } returns listOf(mockk {
            every { toEmployeeEPIResponse() } returns employeeEpiResponse
        })

        val result = employeeEpiService.getEmployeeEpi()

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(employeeEpiResponse, result.first())
        verify(exactly = 1) { employeeEPIRepository.findAll() }
    }

    @Test
    fun `deve buscar EPI do funcionário pelo ID com sucesso`() {
        // Configuração do mock para EmployeeEPIRepository
        val employeeEpiModel = EmployeeEPIModel(
            employeeId = UUID.randomUUID(),
            epiId = UUID.randomUUID(),
            quantity = 1,
            deliveryDate = LocalDate.now(),
            returnDate = null,
            epiStatus = EPIStatus.ENTREGUE,
            reason = "Entrega inicial",
            creationDateId = UUID.randomUUID()
        )
        every { employeeEPIRepository.findById(employeeEPIId) } returns employeeEpiModel

        // Configuração do mock para EmployeeRepository
        val employeeResponse = EmployeeResponse(
            id = employeeEpiModel.employeeId,
            name = "João Silva",
            registration = 1,
            cpf = "12345678901",
            birthDate = LocalDate.now(),
            phones = "11987654321",
            generalRegistration = "GR123",
            hourlyRate = 50.0,
            function = "Developer",
            status = EmployeeStatus.ATIVO,
            addressModel = AddressModel(
                id = UUID.randomUUID(),
                street = "Rua dos Bobos",
                number = "0",
                neighborhood = "Centro",
                city = "São Paulo",
                state = "SP",
                zipCode = "12345-678"
            )
        )
        every { employeeRepository.findEmployeeById(employeeEpiModel.employeeId) } returns employeeResponse

        // Configuração do mock para EPIRepository
        val epiResponse = EpiResponse(
            id = employeeEpiModel.epiId,
            name = "Capacete de Segurança",
            approvalCertificate = "12345",
            quantity = 10,
            unitValue = 50.0,
            manufacturingDate = LocalDate.now(),
            expirationDate = LocalDate.now().plusYears(1),
            tag = "ABC123",
            lot = "LOT123"
        )
        every { epiRepository.findById(employeeEpiModel.epiId) } returns mockk {
            every { toResponse() } returns epiResponse
        }

        // Executa o método a ser testado
        val result = employeeEpiService.getEmployeeEpiId(employeeEPIId)

        // Verificações
        assertNotNull(result)
        assertEquals(employeeEpiModel.quantity, result.quantity)
        assertEquals(employeeEpiModel.deliveryDate, result.deliveryDate)
        assertEquals(employeeEpiModel.returnDate, result.returnDate)
        assertEquals(employeeEpiModel.epiStatus, result.epiStatus)
        assertEquals(employeeEpiModel.reason, result.reason)
        assertEquals(employeeEpiModel.creationDateId, result.creationDateId)
        assertEquals(employeeResponse, result.employee)
        assertEquals(epiResponse, result.epi)

        // Verifica se os métodos dos repositórios foram chamados corretamente
        verify(exactly = 1) { employeeEPIRepository.findById(employeeEPIId) }
        verify(exactly = 1) { employeeRepository.findEmployeeById(employeeEpiModel.employeeId) }
        verify(exactly = 1) { epiRepository.findById(employeeEpiModel.epiId) }
    }

    @Test
    fun `deve listar EPIs entregues com sucesso`() {
        every { employeeEPIRepository.findByStatus(EPIStatus.ENTREGUE) } returns listOf(employeeEpiResponse)

        val result = employeeEpiService.getEmployeeEpiDelivered()

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(employeeEpiResponse, result.first())
        verify(exactly = 1) { employeeEPIRepository.findByStatus(EPIStatus.ENTREGUE) }
    }

    @Test
    fun `deve listar EPIs devolvidos com sucesso`() {
        every { employeeEPIRepository.findByStatus(EPIStatus.DEVOLVIDO) } returns listOf(employeeEpiResponse)

        val result = employeeEpiService.getEmployeeEpiReturned()

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(employeeEpiResponse, result.first())
        verify(exactly = 1) { employeeEPIRepository.findByStatus(EPIStatus.DEVOLVIDO) }
    }

    @Test
    fun `deve devolver todos os EPIs do funcionário com sucesso`() {
        val employeeId = returnAllEpi.employeeId
        val returnDate = returnAllEpi.returnDate

        val employee = mockk<EmployeeResponse> {
            every { status } returns EmployeeStatus.ATIVO
        }
        val employeeEpiList = listOf(
            mockk<EmployeeEPI> {
                every { returnDate } returns LocalDate.now()
                every { toEmployeeEPIModel() } returns employeeEpiModel
            }
        )
        val updatedEmployeeEpiResponse = employeeEpiResponse.copy(
            returnDate = returnDate,
            epiStatus = EPIStatus.DEVOLVIDO
        )

        every { employeeRepository.findEmployeeById(employeeId) } returns employee
        every { employeeEPIRepository.findByEmployeeIdAndEPIStatus(employeeId) } returns employeeEpiList
        every { employeeEPIRepository.save(any()) } returns updatedEmployeeEpiResponse

        val result = employeeEpiService.returnAllEpiToEmployee(returnAllEpi)

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(returnDate, result.first().returnDate)
        assertEquals(EPIStatus.DEVOLVIDO, result.first().epiStatus)
        verify(exactly = 1) { employeeRepository.findEmployeeById(employeeId) }
        verify(exactly = 1) { employeeEPIRepository.findByEmployeeIdAndEPIStatus(employeeId) }
        verify(exactly = 1) { employeeEPIRepository.save(any()) }
    }

    @Test
    fun `deve lançar exceção ao devolver todos os EPIs de funcionário sem EPIs`() {
        val employeeId = returnAllEpi.employeeId

        every { employeeRepository.findEmployeeById(employeeId) } returns mockk {
            every { status } returns EmployeeStatus.ATIVO
        }
        every { employeeEPIRepository.findByEmployeeIdAndEPIStatus(employeeId) } returns emptyList()

        val exception = assertThrows<Exception> {
            employeeEpiService.returnAllEpiToEmployee(returnAllEpi)
        }

        assertEquals("Funcionário não possui EPIs", exception.message)
        verify(exactly = 1) { employeeRepository.findEmployeeById(employeeId) }
        verify(exactly = 1) { employeeEPIRepository.findByEmployeeIdAndEPIStatus(employeeId) }
        verify(exactly = 0) { employeeEPIRepository.save(any()) }
    }

    @Test
    fun `deve buscar EPIs do funcionário pelo ID com sucesso`() {
        val employeeId = UUID.randomUUID()

        every { employeeEPIRepository.findByEmployeeId(employeeId) } returns listOf(mockk {
            every { toEmployeeEPIResponse() } returns employeeEpiResponse
        })

        val result = employeeEpiService.getEmployeeEpiByEmployeeId(employeeId)

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(employeeEpiResponse, result.first())
        verify(exactly = 1) { employeeEPIRepository.findByEmployeeId(employeeId) }
    }

    @Test
    fun `deve buscar EPIs pelo ID do EPI com sucesso`() {
        val epiId = UUID.randomUUID()

        every { employeeEPIRepository.findByEpiId(epiId) } returns listOf(mockk {
            every { toEmployeeEPIResponse() } returns employeeEpiResponse
        })

        val result = employeeEpiService.getEmployeeEpiByEpiId(epiId)

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(employeeEpiResponse, result.first())
        verify(exactly = 1) { employeeEPIRepository.findByEpiId(epiId) }
    }

    @Test
    fun `deve buscar EPIs entregues pelo ID do funcionário com sucesso`() {
        val employeeId = UUID.randomUUID()

        every { employeeEPIRepository.findByEmployeeIdAndEPIStatus(employeeId) } returns listOf(mockk {
            every { toEmployeeEPIResponse() } returns employeeEpiResponse
        })

        val result = employeeEpiService.getEmployeeEpiDeliveredByEmployeeId(employeeId)

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(employeeEpiResponse, result.first())
        verify(exactly = 1) { employeeEPIRepository.findByEmployeeIdAndEPIStatus(employeeId) }
    }
}