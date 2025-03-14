package database.repository

import com.brayan.renovar.api.request.AddEmployeesWorkRequest
import com.brayan.renovar.api.response.EmployeeResponse
import com.brayan.renovar.database.entities.CreationDate
import com.brayan.renovar.database.entities.EmployeeWork
import com.brayan.renovar.database.entities.EmployeeWorkKey
import com.brayan.renovar.database.repositories.interfaces.CreationRepository
import com.brayan.renovar.database.repositories.interfaces.EmployeeRepository
import com.brayan.renovar.database.repositories.interfaces.EmployeeWorkRespository
import com.brayan.renovar.database.repositories.interfaces.WorkRepository
import com.brayan.renovar.enum.EmployeeStatus
import com.brayan.renovar.enum.WorkStatus
import com.brayan.renovar.models.EmployeeWorkModel
import com.brayan.renovar.models.WorkModel
import com.brayan.renovar.services.implementations.EmployeeWorkServiceImpl
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
import java.util.Optional
import java.util.UUID

class EmployeeWorkTest {
    private val employeeWorkRepository: EmployeeWorkRespository = mockk(relaxed = true)
    private val creationRepository: CreationRepository = mockk()
    private val workRepository: WorkRepository = mockk()
    private val employeeRepository: EmployeeRepository = mockk()
    private val employeeWorkService = EmployeeWorkServiceImpl(
        employeeWorkRepository,
        creationRepository,
        workRepository,
        employeeRepository
    )

    private lateinit var work: WorkModel
    private lateinit var employee: EmployeeResponse
    private lateinit var addEmployeesWorkRequest: AddEmployeesWorkRequest
    private lateinit var employeeWorkModel: EmployeeWorkModel
    private lateinit var id: UUID
    private lateinit var creationDate: CreationDate

    @BeforeEach
    fun setUp() {
        id = UUID.randomUUID()
        work = WorkModel(id, "Empresa X", "12345678000100", WorkStatus.ATIVA, null, null, mockk(), mutableListOf(), mutableListOf())
        employee = EmployeeResponse(id, "João", 123, "123.456.789-00", LocalDate.now(), "(62) 99999-9999", "MG-12.345.678", 18.0, "Soldador", EmployeeStatus.ATIVO, mockk())
        addEmployeesWorkRequest = AddEmployeesWorkRequest(id, id, LocalDate.now())
        employeeWorkModel = EmployeeWorkModel(id, id, LocalDate.now(), null, null, null, id)
    }

    @Test
    fun `deve salvar funcionario em uma obra com sucesso`() {
        every { employeeWorkRepository.findByEmployeeIdAndEndDateIsNull(id) } returns emptyList()
        every { workRepository.findById(id) } returns work
        every { employeeRepository.findEmployeeById(id) } returns employee
        every { creationRepository.saveCreation() } returns UUID.randomUUID()
        every { creationRepository.findCreationById(any()) } returns mockk() // Adicionado
        every { employeeWorkRepository.save(any()) } returns employeeWorkModel

        val result = employeeWorkService.saveEmployeeWork(addEmployeesWorkRequest)

        assertNotNull(result)
        verify(exactly = 1) { employeeWorkRepository.save(any()) }
    }



    @Test
    fun `nao deve permitir funcionario em mais de uma obra`() {
        every { employeeWorkRepository.findByEmployeeIdAndEndDateIsNull(id) } returns listOf(employeeWorkModel)

        val exception = assertThrows<Exception> {
            employeeWorkService.saveEmployeeWork(addEmployeesWorkRequest)
        }

        assertEquals("Funcionário já está trabalhando em outra obra", exception.message)
    }

    @Test
    fun `nao deve permitir funcionario em obra finalizada`() {
        val workFinalizada = work.copy(workStatus = WorkStatus.FINALIZADA)
        every { workRepository.findById(id) } returns workFinalizada

        val exception = assertThrows<Exception> {
            employeeWorkService.saveEmployeeWork(addEmployeesWorkRequest)
        }

        assertEquals("Obra finalizada", exception.message)
    }

    @Test
    fun `nao deve permitir funcionario inativo ou demitido`() {
        val employeeInativo = employee.copy(status = EmployeeStatus.INATIVO)
        every { employeeRepository.findEmployeeById(id) } returns employeeInativo
        every { workRepository.findById(id) } returns work // Garantir que esse mock está correto

        val exception = assertThrows<Exception> {
            employeeWorkService.saveEmployeeWork(addEmployeesWorkRequest)
        }

        assertEquals("Funcionário não está trabalhando", exception.message)
    }


    @Test
    fun `deve obter lista de funcionarios em obras`() {
        every { employeeWorkRepository.findAll() } returns listOf(employeeWorkModel)
        every { workRepository.findById(id) } returns work
        every { employeeRepository.findEmployeeById(id) } returns employee
        every { creationRepository.findCreationById(id) } returns mockk() // Adicionando mock necessário

        val result = employeeWorkService.getEmployeeWork()

        assertEquals(1, result.size)
    }



}