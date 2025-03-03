package database.repository

import com.brayan.renovar.api.request.WorkUpdateRequest
import com.brayan.renovar.api.response.WorkResponse
import com.brayan.renovar.database.repositories.interfaces.WorkRepository
import com.brayan.renovar.enum.WorkStatus
import com.brayan.renovar.models.AddressModel
import com.brayan.renovar.models.WorkModel
import com.brayan.renovar.services.implementations.WorkServiceImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.UUID

class WorkTest {
    private val workRepository: WorkRepository = mockk()
    private val workService = WorkServiceImpl(workRepository)

    private lateinit var workModel: WorkModel
    private lateinit var workResponse: WorkResponse
    private lateinit var workUpdateRequest: WorkUpdateRequest
    private lateinit var id: UUID

    @BeforeEach
    fun setUp() {
        id = UUID.randomUUID()
        workModel = WorkModel(
            id = id,
            companyProviding = "Empresa A",
            cnpj = "12345678901234",
            workStatus = WorkStatus.ATIVA,
            address = AddressModel(
                street = "Rua A",
                number = "123",
                neighborhood = "Bairro A",
                city = "Cidade A",
                state = "Estado A",
                zipCode = "12345678"
            ),
            creationDate = LocalDateTime.now(),
            updateDate = LocalDateTime.now()
        )
        workResponse = WorkResponse(
            id = id,
            companyProviding = "Empresa A",
            cnpj = "12345678901234",
            workStatus = WorkStatus.ATIVA,
            address = AddressModel(
                street = "Rua A",
                number = "123",
                neighborhood = "Bairro A",
                city = "Cidade A",
                state = "Estado A",
                zipCode = "12345678"
            ),
            creationDate = LocalDateTime.now(),
            updateDate = LocalDateTime.now()
        )
        workUpdateRequest = WorkUpdateRequest(
            id = id,
            companyProviding = "Empresa B",
            workStatus = WorkStatus.FINALIZADA
        )
    }

    @Test
    fun `deve criar trabalho com sucesso`() {
        every { workRepository.save(workModel) } returns workModel

        val result = workService.createWork(workModel)

        assertNotNull(result)
        assertEquals(workResponse, result)
        verify(exactly = 1) { workRepository.save(workModel) }
    }

    @Test
    fun `deve atualizar trabalho com sucesso`() {
        val updatedWorkModel = workModel.copy(
            companyProviding = "Empresa B",
            workStatus = WorkStatus.FINALIZADA
        )
        every { workRepository.findById(id) } returns workModel
        every { workRepository.save(updatedWorkModel) } returns updatedWorkModel

        val result = workService.updateWork(workUpdateRequest)

        assertNotNull(result)
        assertEquals("Empresa B", result.companyProviding)
        assertEquals(WorkStatus.FINALIZADA, result.workStatus)
        verify(exactly = 1) { workRepository.findById(id) }
        verify(exactly = 1) { workRepository.save(updatedWorkModel) }
    }

    @Test
    fun `deve listar todos os trabalhos com sucesso`() {
        every { workRepository.findAll() } returns listOf(workModel)

        val result = workService.getWorks()

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(workResponse, result.first())
        verify(exactly = 1) { workRepository.findAll() }
    }

    @Test
    fun `deve buscar trabalho pelo ID com sucesso`() {
        every { workRepository.findById(id) } returns workModel

        val result = workService.getWorkById(id)

        assertNotNull(result)
        assertEquals(workResponse, result)
        verify(exactly = 1) { workRepository.findById(id) }
    }

    @Test
    fun `deve buscar trabalhos pelo status com sucesso`() {
        val status = WorkStatus.ATIVA
        every { workRepository.findByWorkStatus(status) } returns listOf(workModel)

        val result = workService.getWorksByStatus(status)

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(workResponse, result.first())
        verify(exactly = 1) { workRepository.findByWorkStatus(status) }
    }

    @Test
    fun `deve buscar trabalhos pela empresa fornecedora com sucesso`() {
        val companyProviding = "Empresa A"
        every { workRepository.findByCompanyProviding(companyProviding) } returns listOf(workModel)

        val result = workService.getWorksByCompanyProviding(companyProviding)

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(workResponse, result.first())
        verify(exactly = 1) { workRepository.findByCompanyProviding(companyProviding) }
    }
}