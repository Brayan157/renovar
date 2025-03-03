package database.repository

import com.brayan.renovar.api.request.ToolUpdateRequest
import com.brayan.renovar.api.response.ToolResponse
import com.brayan.renovar.database.repositories.interfaces.ToolRepository
import com.brayan.renovar.enum.ToolStatus
import com.brayan.renovar.models.ToolModel
import com.brayan.renovar.services.implementations.ToolServiceImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import java.time.LocalDate
import java.util.UUID

class ToolTest {
    private val toolRepository: ToolRepository = mockk()
    private val toolService = ToolServiceImpl(toolRepository)

    private lateinit var toolModel: ToolModel
    private lateinit var toolResponse: ToolResponse
    private lateinit var toolUpdateRequest: ToolUpdateRequest
    private lateinit var id: UUID

    @BeforeEach
    fun setUp() {
        id = UUID.randomUUID()
        toolModel = ToolModel(
            id = id,
            name = "Martelo",
            purchaseDate = LocalDate.now(),
            unitValue = 50.0,
            toolStatus = ToolStatus.TRABALHANDO,
            quantity = 10
        )
        toolResponse = ToolResponse(
            id = id,
            name = "Martelo",
            purchaseDate = LocalDate.now(),
            unitValue = 50.0,
            toolStatus = ToolStatus.TRABALHANDO,
            quantity = 10
        )
        toolUpdateRequest = ToolUpdateRequest(
            id = id,
            toolStatus = ToolStatus.MANUTENCAO
        )
    }

    @Test
    fun `deve salvar ferramenta com sucesso`() {
        every { toolRepository.save(toolModel) } returns toolModel

        val result = toolService.save(toolModel)

        assertNotNull(result)
        assertEquals(toolResponse, result)
        verify(exactly = 1) { toolRepository.save(toolModel) }
    }

    @Test
    fun `deve listar todas as ferramentas com sucesso`() {
        every { toolRepository.findAll() } returns listOf(toolModel)

        val result = toolService.findAll()

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(toolResponse, result.first())
        verify(exactly = 1) { toolRepository.findAll() }
    }

    @Test
    fun `deve buscar ferramenta pelo ID com sucesso`() {
        every { toolRepository.findById(id) } returns toolModel

        val result = toolService.findById(id)

        assertNotNull(result)
        assertEquals(toolResponse, result)
        verify(exactly = 1) { toolRepository.findById(id) }
    }

    @Test
    fun `deve buscar ferramentas pelo nome com sucesso`() {
        val name = "Martelo"
        every { toolRepository.findByName(name) } returns listOf(toolModel)

        val result = toolService.findByName(name)

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(toolResponse, result.first())
        verify(exactly = 1) { toolRepository.findByName(name) }
    }

    @Test
    fun `deve buscar ferramentas pelo status com sucesso`() {
        val status = ToolStatus.TRABALHANDO
        every { toolRepository.findByStatus(status) } returns listOf(toolModel)

        val result = toolService.findByStatus(status)

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(toolResponse, result.first())
        verify(exactly = 1) { toolRepository.findByStatus(status) }
    }

    @Test
    fun `deve deletar ferramenta com sucesso`() {
        val deletedToolModel = toolModel.copy(toolStatus = ToolStatus.DELETADA)
        every { toolRepository.findById(id) } returns toolModel
        every { toolRepository.save(deletedToolModel) } returns deletedToolModel

        val result = toolService.deleteById(id)

        assertTrue(result)
        verify(exactly = 1) { toolRepository.findById(id) }
        verify(exactly = 1) { toolRepository.save(deletedToolModel) }
    }

    @Test
    fun `deve buscar ferramentas com status diferente de DELETADA com sucesso`() {
        val status = ToolStatus.DELETADA
        every { toolRepository.findByStatusNot(status) } returns listOf(toolModel)

        val result = toolService.findByStatusNot(status)

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(toolResponse, result.first())
        verify(exactly = 1) { toolRepository.findByStatusNot(status) }
    }

    @Test
    fun `deve atualizar ferramenta com sucesso`() {
        val updatedToolModel = toolModel.copy(toolStatus = ToolStatus.MANUTENCAO)
        every { toolRepository.findById(id) } returns toolModel
        every { toolRepository.save(updatedToolModel) } returns updatedToolModel

        val result = toolService.update(toolUpdateRequest)

        assertNotNull(result)
        assertEquals(ToolStatus.MANUTENCAO, result.toolStatus)
        verify(exactly = 1) { toolRepository.findById(id) }
        verify(exactly = 1) { toolRepository.save(updatedToolModel) }
    }
}