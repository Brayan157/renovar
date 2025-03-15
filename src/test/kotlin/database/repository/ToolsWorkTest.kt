package database.repository

import com.brayan.renovar.api.request.ToolsWorkResquest
import com.brayan.renovar.api.request.ToolsWorkUpdateResquest
import com.brayan.renovar.database.entities.ToolsWorkId
import com.brayan.renovar.database.repositories.interfaces.CreationRepository
import com.brayan.renovar.database.repositories.interfaces.ToolRepository
import com.brayan.renovar.database.repositories.interfaces.ToolsWorkRepository
import com.brayan.renovar.database.repositories.interfaces.WorkRepository
import com.brayan.renovar.enum.ToolStatus
import com.brayan.renovar.enum.WorkStatus
import com.brayan.renovar.models.AddressModel
import com.brayan.renovar.models.ToolModel
import com.brayan.renovar.models.ToolsWorkModel
import com.brayan.renovar.models.WorkModel
import com.brayan.renovar.services.implementations.ToolsWorkServiceImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.verify
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

class ToolsWorkTest {
    private val toolsWorkRepository: ToolsWorkRepository = mockk()
    private val creationRepository: CreationRepository = mockk()
    private val toolRepository: ToolRepository = mockk()
    private val workRepository: WorkRepository = mockk()

    private lateinit var toolsWorkService: ToolsWorkServiceImpl

    private lateinit var toolsWorkRequest: ToolsWorkResquest
    private lateinit var toolsWorkUpdateRequest: ToolsWorkUpdateResquest
    private lateinit var toolsWorkModel: ToolsWorkModel
    private lateinit var toolModel: ToolModel
    private lateinit var workModel: WorkModel

    @BeforeEach
    fun setUp() {
        toolsWorkService = ToolsWorkServiceImpl(toolsWorkRepository, creationRepository, toolRepository, workRepository)

        toolsWorkRequest = ToolsWorkResquest(
            toolsId = UUID.randomUUID(),
            workId = UUID.randomUUID(),
            reason = "Test Reason",
            entryDate = LocalDate.now()
        )

        toolsWorkUpdateRequest = ToolsWorkUpdateResquest(
            toolsId = UUID.randomUUID(),
            workId = UUID.randomUUID(),
            creationDateId = UUID.randomUUID(),
            exitDate = LocalDate.now()
        )

        toolsWorkModel = ToolsWorkModel(
            toolsId = toolsWorkRequest.toolsId,
            workId = toolsWorkRequest.workId,
            reason = toolsWorkRequest.reason,
            entryDate = toolsWorkRequest.entryDate,
            creationDateId = UUID.randomUUID()
        )

        toolModel = ToolModel(
            id = toolsWorkRequest.toolsId,
            name = "Test Tool",
            purchaseDate = LocalDate.now(),
            unitValue = 100.0,
            toolStatus = ToolStatus.PARADA,
            quantity = 1
        )

        workModel = WorkModel(
            id = toolsWorkRequest.workId,
            companyProviding = "Test Company",
            cnpj = "12345678901234",
            workStatus = WorkStatus.ATIVA,
            address = AddressModel(
                street = "Test Street",
                number = "123",
                city = "Test City",
                state = "TS",
                zipCode = "12345-678"
            )
        )
    }

    @Test
    fun `deve lançar exceção ao salvar quando a ferramenta já está em uso`() {
        every { toolsWorkRepository.findByToolIdAndExitDateIsNull(toolsWorkRequest.toolsId) } returns listOf(toolsWorkModel)

        val exception = assertThrows<Exception> {
            toolsWorkService.save(toolsWorkRequest)
        }

        assertEquals("Ferramenta já está sendo utilizada em outra obra", exception.message)
        verify(exactly = 1) { toolsWorkRepository.findByToolIdAndExitDateIsNull(toolsWorkRequest.toolsId) }
    }

    @Test
    fun `deve salvar com sucesso quando a ferramenta não está em uso`() {
        every { toolsWorkRepository.findByToolIdAndExitDateIsNull(toolsWorkRequest.toolsId) } returns emptyList()
        every { creationRepository.saveCreation() } returns UUID.randomUUID()
        every { toolRepository.findById(toolsWorkRequest.toolsId) } returns toolModel
        every { workRepository.findById(toolsWorkRequest.workId) } returns workModel
        every { toolsWorkRepository.save(any()) } returns toolsWorkModel

        val result = toolsWorkService.save(toolsWorkRequest)

        assertNotNull(result)
        assertEquals(toolsWorkRequest.toolsId, result.tool.id)
        assertEquals(toolsWorkRequest.workId, result.workResponse.id)
        verify(exactly = 1) { toolsWorkRepository.findByToolIdAndExitDateIsNull(toolsWorkRequest.toolsId) }
        verify(exactly = 1) { creationRepository.saveCreation() }
        verify(exactly = 1) { toolRepository.findById(toolsWorkRequest.toolsId) }
        verify(exactly = 1) { workRepository.findById(toolsWorkRequest.workId) }
        verify(exactly = 1) { toolsWorkRepository.save(any()) }
    }

    @Test
    fun `deve atualizar com sucesso`() {
        every { toolsWorkRepository.findById(any()) } returns toolsWorkModel
        every { toolRepository.findById(toolsWorkUpdateRequest.toolsId) } returns toolModel
        every { workRepository.findById(toolsWorkUpdateRequest.workId) } returns workModel
        every { toolsWorkRepository.save(any()) } returns toolsWorkModel.copy(exitDate = toolsWorkUpdateRequest.exitDate)

        val result = toolsWorkService.update(toolsWorkUpdateRequest)

        assertNotNull(result)
        assertEquals(toolsWorkUpdateRequest.exitDate, result.exitDate)
        verify(exactly = 1) { toolsWorkRepository.findById(any()) }
        verify(exactly = 1) { toolRepository.findById(toolsWorkUpdateRequest.toolsId) }
        verify(exactly = 1) { workRepository.findById(toolsWorkUpdateRequest.workId) }
        verify(exactly = 1) { toolsWorkRepository.save(any()) }
    }

    @Test
    fun `deve listar todos com sucesso`() {
        every { toolsWorkRepository.findAll() } returns listOf(toolsWorkModel)
        every { toolRepository.findById(toolsWorkModel.toolsId) } returns toolModel
        every { workRepository.findById(toolsWorkModel.workId) } returns workModel

        val result = toolsWorkService.listAll()

        assertNotNull(result)
        assertEquals(1, result.size)
        verify(exactly = 1) { toolsWorkRepository.findAll() }
        verify(exactly = 1) { toolRepository.findById(toolsWorkModel.toolsId) }
        verify(exactly = 1) { workRepository.findById(toolsWorkModel.workId) }
    }

    @Test
    fun `deve listar por workId com sucesso`() {
        every { toolsWorkRepository.findByWorkId(toolsWorkRequest.workId) } returns listOf(toolsWorkModel)
        every { toolRepository.findById(toolsWorkModel.toolsId) } returns toolModel
        every { workRepository.findById(toolsWorkModel.workId) } returns workModel

        val result = toolsWorkService.listByWorkId(toolsWorkRequest.workId)

        assertNotNull(result)
        assertEquals(1, result.size)
        verify(exactly = 1) { toolsWorkRepository.findByWorkId(toolsWorkRequest.workId) }
        verify(exactly = 1) { toolRepository.findById(toolsWorkModel.toolsId) }
        verify(exactly = 1) { workRepository.findById(toolsWorkModel.workId) }
    }

    @Test
    fun `deve listar por workId e status emprestado com sucesso`() {
        every { toolsWorkRepository.findByWorkIdAndExitDateIsNull(toolsWorkRequest.workId) } returns listOf(toolsWorkModel)
        every { toolRepository.findById(toolsWorkModel.toolsId) } returns toolModel
        every { workRepository.findById(toolsWorkModel.workId) } returns workModel

        val result = toolsWorkService.listByWorkIdAndStatusLoaned(toolsWorkRequest.workId)

        assertNotNull(result)
        assertEquals(1, result.size)
        verify(exactly = 1) { toolsWorkRepository.findByWorkIdAndExitDateIsNull(toolsWorkRequest.workId) }
        verify(exactly = 1) { toolRepository.findById(toolsWorkModel.toolsId) }
        verify(exactly = 1) { workRepository.findById(toolsWorkModel.workId) }
    }

    @Test
    fun `deve listar por workId e status devolvido com sucesso`() {
        every { toolsWorkRepository.findByWorkIdAndExitDateIsNotNull(toolsWorkRequest.workId) } returns listOf(toolsWorkModel.copy(exitDate = LocalDate.now()))
        every { toolRepository.findById(toolsWorkModel.toolsId) } returns toolModel
        every { workRepository.findById(toolsWorkModel.workId) } returns workModel

        val result = toolsWorkService.listByWorkIdAndStatusReturned(toolsWorkRequest.workId)

        assertNotNull(result)
        assertEquals(1, result.size)
        verify(exactly = 1) { toolsWorkRepository.findByWorkIdAndExitDateIsNotNull(toolsWorkRequest.workId) }
        verify(exactly = 1) { toolRepository.findById(toolsWorkModel.toolsId) }
        verify(exactly = 1) { workRepository.findById(toolsWorkModel.workId) }
    }

    @Test
    fun `deve listar por workId e toolId com sucesso`() {
        val toolsWorkId = ToolsWorkId(toolsWorkRequest.toolsId, toolsWorkRequest.workId, UUID.randomUUID())
        every { toolsWorkRepository.findById(toolsWorkId) } returns toolsWorkModel
        every { toolRepository.findById(toolsWorkModel.toolsId) } returns toolModel
        every { workRepository.findById(toolsWorkModel.workId) } returns workModel

        val result = toolsWorkService.listByWorkIdAndToolId(toolsWorkId)

        assertNotNull(result)
        assertEquals(toolsWorkRequest.toolsId, result.tool.id)
        assertEquals(toolsWorkRequest.workId, result.workResponse.id)
        verify(exactly = 1) { toolsWorkRepository.findById(toolsWorkId) }
        verify(exactly = 1) { toolRepository.findById(toolsWorkModel.toolsId) }
        verify(exactly = 1) { workRepository.findById(toolsWorkModel.workId) }
    }
}