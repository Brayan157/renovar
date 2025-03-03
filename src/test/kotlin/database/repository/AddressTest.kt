package database.repository
import com.brayan.renovar.api.request.AddressUpdateRequest
import com.brayan.renovar.database.repositories.interfaces.AddressRepository
import com.brayan.renovar.models.AddressModel
import com.brayan.renovar.services.implementations.AddressServiceImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.UUID

class AddressTest {
    private val addressRepository: AddressRepository = mockk()
    private val addressService = AddressServiceImpl(addressRepository)

    private lateinit var addressModel: AddressModel
    private lateinit var addressUpdateRequest: AddressUpdateRequest
    private lateinit var id: UUID

    @BeforeEach
    fun setUp() {
        id = UUID.randomUUID()
        addressModel = AddressModel(id, "Rua A", "123", "Bairro X", "Cidade Y", "Estado Z", "12345-678", "Apto 101", LocalDateTime.now(), LocalDateTime.now())
        addressUpdateRequest = AddressUpdateRequest(id, "Rua B", null, null, null, null, null, null, LocalDateTime.now(), LocalDateTime.now())
    }

    @Test
    fun `deve criar endereço com sucesso`() {
        every { addressRepository.save(addressModel) } returns addressModel

        val result = addressService.createAddress(addressModel)

        assertNotNull(result)
        assertEquals(addressModel, result)
        verify(exactly = 1) { addressRepository.save(addressModel) }
    }

    @Test
    fun `deve atualizar endereço com sucesso`() {
        every { addressRepository.findById(id) } returns addressModel
        every { addressRepository.save(any()) } returns addressModel.copy(street = "Rua B")

        val result = addressService.updateAddress(addressUpdateRequest)

        assertNotNull(result)
        assertEquals("Rua B", result.street)
        verify(exactly = 1) { addressRepository.findById(id) }
        verify(exactly = 1) { addressRepository.save(any()) }
    }

    @Test
    fun `deve obter endereço pelo ID com sucesso`() {
        every { addressRepository.findById(id) } returns addressModel

        val result = addressService.getAddressById(id)

        assertNotNull(result)
        assertEquals(addressModel, result)
        verify(exactly = 1) { addressRepository.findById(id) }
    }

}