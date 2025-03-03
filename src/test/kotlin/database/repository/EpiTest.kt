package database.repository

import com.brayan.renovar.api.request.EPIUpdateRequest
import com.brayan.renovar.api.response.EpiResponse
import com.brayan.renovar.database.repositories.interfaces.EPIRepository
import com.brayan.renovar.models.EPIModel
import com.brayan.renovar.services.implementations.EPIserviceImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import java.time.LocalDate
import java.util.UUID

class EpiTest {
    private val epiRepository: EPIRepository = mockk()
    private val epiService = EPIserviceImpl(epiRepository)

    private lateinit var epiModel: EPIModel
    private lateinit var epiUpdateRequest: EPIUpdateRequest
    private lateinit var epiResponse: EpiResponse
    private lateinit var id: UUID

    @BeforeEach
    fun setUp() {
        id = UUID.randomUUID()
        epiModel = EPIModel(
            id = id,
            name = "Capacete de Segurança",
            approvalCertificate = "12345",
            quantity = 10,
            unitValue = 50.0,
            manufacturingDate = LocalDate.now(),
            expirationDate = LocalDate.now().plusYears(1),
            tag = "ABC123",
            lot = "LOT123"
        )
        epiUpdateRequest = EPIUpdateRequest(
            id = id,
            name = "Capacete Atualizado",
            quantity = 20,
            unitValue = 60.0
        )
        epiResponse = EpiResponse(
            id = id,
            name = "Capacete de Segurança",
            approvalCertificate = "12345",
            quantity = 10,
            unitValue = 50.0,
            manufacturingDate = LocalDate.now(),
            expirationDate = LocalDate.now().plusYears(1),
            tag = "ABC123",
            lot = "LOT123"
        )
    }
    @Test
    fun `deve salvar EPI com sucesso`() {
        every { epiRepository.save(epiModel) } returns epiModel.toResponse()

        val result = epiService.save(epiModel)

        assertNotNull(result)
        assertEquals(epiResponse, result)
        verify(exactly = 1) { epiRepository.save(epiModel) }
    }

    @Test
    fun `deve atualizar EPI com sucesso`() {
        val updatedEpiModel = epiModel.copy(
            name = "Capacete Atualizado",
            quantity = 20,
            unitValue = 60.0
        )
        val updatedEpiResponse = epiResponse.copy(
            name = "Capacete Atualizado",
            quantity = 20,
            unitValue = 60.0
        )
        every { epiRepository.findById(id) } returns updatedEpiModel
        every { epiRepository.save(any()) } returns updatedEpiModel.toResponse()

        val result = epiService.update(epiUpdateRequest)

        assertNotNull(result)
        assertEquals(updatedEpiResponse, result)
        verify(exactly = 1) { epiRepository.findById(id) }
        verify(exactly = 1) { epiRepository.save(any()) }
    }

    @Test
    fun `deve listar EPIs expirados com sucesso`() {
        val currentDate = LocalDate.now()
        val expiredEPIs = listOf(epiModel.copy(expirationDate = LocalDate.now().minusDays(1)))
        every { epiRepository.listExpiredEPIs(currentDate) } returns expiredEPIs.map { it.toResponse() }

        val result = epiService.listExpiredEPIs()

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(expiredEPIs.first().toResponse(), result.first())
        verify(exactly = 1) { epiRepository.listExpiredEPIs(currentDate) }
    }

    @Test
    fun `deve listar todos os EPIs com sucesso`() {
        val allEPIs = listOf(epiModel)
        every { epiRepository.findAll() } returns allEPIs.map { it.toResponse() }

        val result = epiService.listAllEPIs()

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(allEPIs.first().toResponse(), result.first())
        verify(exactly = 1) { epiRepository.findAll() }
    }

    @Test
    fun `deve buscar EPI pelo ID com sucesso`() {
        every { epiRepository.findById(id) } returns epiModel

        val result = epiService.findById(id)

        assertNotNull(result)
        assertEquals(epiResponse, result)
        verify(exactly = 1) { epiRepository.findById(id) }
    }

    @Test
    fun `deve buscar EPIs pelo nome com sucesso`() {
        val name = "Capacete"
        val epiList = listOf(epiModel)
        every { epiRepository.findByName(name) } returns epiList.map { it.toResponse() }

        val result = epiService.findByName(name)

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(epiList.first().toResponse(), result.first())
        verify(exactly = 1) { epiRepository.findByName(name) }
    }

}