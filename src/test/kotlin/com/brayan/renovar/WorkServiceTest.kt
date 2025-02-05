package com.brayan.renovar

import com.brayan.renovar.api.request.AddEmployeesWorkRequest
import com.brayan.renovar.api.request.RemoveEmployeesWork
import com.brayan.renovar.api.request.WorkUpdateRequest
import com.brayan.renovar.database.entities.Address
import com.brayan.renovar.database.entities.Employee
import com.brayan.renovar.database.entities.Function
import com.brayan.renovar.database.repositories.interfaces.EmployeeRepository
import com.brayan.renovar.database.repositories.interfaces.ToolRepository
import com.brayan.renovar.database.repositories.interfaces.WorkRepository
import com.brayan.renovar.enum.EmployeeStatus
import com.brayan.renovar.enum.WorkStatus
import com.brayan.renovar.models.AddressModel
import com.brayan.renovar.models.EmployeeWorkModel
import com.brayan.renovar.models.FunctionModel
import com.brayan.renovar.models.WorkModel
import com.brayan.renovar.services.implementations.WorkServiceImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Optional
import java.util.UUID

class WorkServiceTest {
    private val workRepository = mockk<WorkRepository>()
    private val employeeRepository = mockk<EmployeeRepository>()
    private val toolRepository = mockk<ToolRepository>()
    private val workServiceImpl = WorkServiceImpl(workRepository, employeeRepository, toolRepository)

    private val addressModel = AddressModel(
        id = UUID.randomUUID(),
        street = "street",
        city = "city",
        number = "123",
        neighborhood = "neighborhood",
        state = "state",
        zipCode = "12345",
        complement = "complement"
    )
    private val functionModel = FunctionModel(
        id = UUID.randomUUID(),
        function = "function 2",
        description = "description test"
    )

    @Test
    fun `should save a work`() {
        val workModel = WorkModel(
            id = UUID.randomUUID(),
            companyProviding = "companyProviding",
            cnpj = "cnpj",
            workStatus = WorkStatus.ATIVA,
            address = addressModel,
            employeesWorks = emptyList(),
            toolsWorks = emptyList()
        )
        val savedWork = slot<WorkModel>()
        every { workRepository.save(capture(savedWork)) } answers { savedWork.captured }
        val result = workServiceImpl.createWork(workModel)
        Assertions.assertNotNull(result)
        Assertions.assertEquals(workModel.id, savedWork.captured.id)
        Assertions.assertTrue(savedWork.captured.employeesWorks.isEmpty())
    }
    @Test
    fun `updateWork should update and return the updated WorkModel`() {
        // Arrange
        val workId = UUID.randomUUID()
        val workUpdateRequest = WorkUpdateRequest(
            id = workId,
            companyProviding = "Updated Company",
            workStatus = WorkStatus.FINALIZADA
        )
        val existingWorkModel = WorkModel(
            id = workId,
            companyProviding = "Original Company",
            cnpj = "12345678901234",
            workStatus = WorkStatus.ATIVA,
            address = addressModel,
            employeesWorks = emptyList(),
            toolsWorks = emptyList()
        )
        val updatedWorkModel = existingWorkModel.copy(
            companyProviding = "Updated Company",
            workStatus = WorkStatus.FINALIZADA
        )

        every { workRepository.findById(workId) } returns existingWorkModel
        every { workRepository.save(updatedWorkModel) } returns updatedWorkModel

        // Act
        val result = workServiceImpl.updateWork(workUpdateRequest)

        // Assert
        assertNotNull(result)
        assertEquals("Updated Company", result.companyProviding)
        assertEquals(WorkStatus.FINALIZADA, result.workStatus)
        verify(exactly = 1) { workRepository.findById(workId) }
        verify(exactly = 1) { workRepository.save(updatedWorkModel) }
    }
    @Test
    fun `getWorks should return a list of WorkModel`() {
        // Arrange
        val workModel1 = WorkModel(
            id = UUID.randomUUID(),
            companyProviding = "Company A",
            cnpj = "12345678901234",
            workStatus = WorkStatus.ATIVA,
            address = addressModel,
            employeesWorks = emptyList(),
            toolsWorks = emptyList()
        )
        val workModel2 = WorkModel(
            id = UUID.randomUUID(),
            companyProviding = "Company B",
            cnpj = "98765432109876",
            workStatus = WorkStatus.FINALIZADA,
            address = addressModel,
            employeesWorks = emptyList(),
            toolsWorks = emptyList()
        )
        val workList = listOf(workModel1, workModel2)

        every { workRepository.findAll() } returns workList

        // Act
        val result = workServiceImpl.getWorks()

        // Assert
        assertEquals(2, result.size)
        assertEquals("Company A", result[0].companyProviding)
        assertEquals("Company B", result[1].companyProviding)
        verify(exactly = 1) { workRepository.findAll() }
    }
    @Test
    fun `getWorkById should return a WorkModel when found`() {
        // Arrange
        val workId = UUID.randomUUID()
        val workModel = WorkModel(
            id = workId,
            companyProviding = "Company A",
            cnpj = "12345678901234",
            workStatus = WorkStatus.ATIVA,
            address = addressModel,
            employeesWorks = emptyList(),
            toolsWorks = emptyList()
        )

        every { workRepository.findById(workId) } returns workModel

        // Act
        val result = workServiceImpl.getWorkById(workId)

        // Assert
        assertNotNull(result)
        assertEquals(workId, result.id)
        assertEquals("Company A", result.companyProviding)
        verify(exactly = 1) { workRepository.findById(workId) }
    }

    @Test
    fun `getWorkById should throw exception when work does not exist`() {
        // Arrange
        val workId = UUID.randomUUID()

        every { workRepository.findById(workId) } throws NoSuchElementException("Work not found")

        // Act & Assert
        val exception = assertThrows<NoSuchElementException> {
            workServiceImpl.getWorkById(workId)
        }

        // Verifica se a mensagem da exceção está correta
        assertEquals("Work not found", exception.message)

        // Verifica se o método do repositório foi chamado exatamente uma vez
        verify(exactly = 1) { workRepository.findById(workId) }
    }
    @Test
    fun `getWorksByStatus should return a list of WorkModel filtered by status`() {
        // Arrange
        val status = WorkStatus.ATIVA
        val workModel1 = WorkModel(
            id = UUID.randomUUID(),
            companyProviding = "Company A",
            cnpj = "12345678901234",
            workStatus = WorkStatus.ATIVA,
            address = addressModel,
            employeesWorks = emptyList(),
            toolsWorks = emptyList()
        )
        val workModel2 = WorkModel(
            id = UUID.randomUUID(),
            companyProviding = "Company B",
            cnpj = "98765432109876",
            workStatus = WorkStatus.ATIVA,
            address = addressModel,
            employeesWorks = emptyList(),
            toolsWorks = emptyList()
        )
        val workList = listOf(workModel1, workModel2)

        every { workRepository.findByWorkStatus(status) } returns workList

        // Act
        val result = workServiceImpl.getWorksByStatus(status)

        // Assert
        assertEquals(2, result.size)
        assertEquals("Company A", result[0].companyProviding)
        assertEquals("Company B", result[1].companyProviding)
        verify(exactly = 1) { workRepository.findByWorkStatus(status) }
    }
    @Test
    fun `addEmployeeToWork should add employees to work and return updated WorkModel`() {
        // Arrange
        val workId = UUID.randomUUID()
        val employeeId1 = UUID.randomUUID()
        val employeeId2 = UUID.randomUUID()
        val startDate = LocalDate.now()

        val addEmployeesWorkRequest = AddEmployeesWorkRequest(
            workId = workId,
            employees = listOf(employeeId1, employeeId2),
            startDate = startDate
        )

        val work = WorkModel(
            id = workId,
            companyProviding = "Company A",
            cnpj = "12345678901234",
            workStatus = WorkStatus.ATIVA,
            address = addressModel,
            employeesWorks = emptyList(),
            toolsWorks = emptyList()
        )

        val employee1 = Employee(
            id = employeeId1,
            name = "João Silva",
            cpf = "12345678909",
            birthDate = LocalDate.of(1990, 5, 10),
            address = Address.of(addressModel),
            function = Function.of(functionModel),
            generalRegistration = "GR123",
            hourlyRate = 30.0,
            employeeStatus = EmployeeStatus.ATIVO,
            employeesWorks = emptyList(),
            employeeEpis = emptyList(),
            toolsEmployee = emptyList(),
            phones = "62 33252367"
        )

        val employee2 = Employee(
            id = employeeId2,
            name = "Maria Souza",
            cpf = "98765432101",
            birthDate = LocalDate.of(1995, 5, 15),
            address = Address.of(addressModel),
            function = Function.of(functionModel),
            generalRegistration = "GR456",
            hourlyRate = 60.0,
            employeeStatus = EmployeeStatus.ATIVO,
            employeesWorks = emptyList(),
            employeeEpis = emptyList(),
            toolsEmployee = emptyList(),
            phones = "62 33252367"
        )

        every { workRepository.findById(workId) } returns work // Retorna Optional corretamente
        every { employeeRepository.findAllById(listOf(employeeId1, employeeId2)) } returns listOf(employee1, employee2)
        every { workRepository.save(any()) } answers { firstArg() } // Retorna o primeiro argumento passado (cópia correta)

        // Act
        val result = workServiceImpl.addEmployeeToWork(addEmployeesWorkRequest)

        // Assert
        assertEquals(2, result.employeesWorks.size)
        assertEquals(employeeId1, result.employeesWorks[0].employeeId)
        assertEquals(employeeId2, result.employeesWorks[1].employeeId)
        assertEquals(startDate, result.employeesWorks[0].startDate)
        assertEquals(startDate, result.employeesWorks[1].startDate)

        verify(exactly = 1) { workRepository.findById(workId) }
        verify(exactly = 1) { employeeRepository.findAllById(listOf(employeeId1, employeeId2)) }
        verify(exactly = 1) { workRepository.save(any()) }
    }

    @Test
    fun `removeEmployeeFromWork should update endDate for removed employees and return updated WorkModel`() {
        // Arrange
        val workId = UUID.randomUUID()
        val employeeId1 = UUID.randomUUID()
        val employeeId2 = UUID.randomUUID()
        val endDate = LocalDate.now()

        val removeEmployeesWork = RemoveEmployeesWork(
            workId = workId,
            employees = listOf(employeeId1),
            endDate = endDate
        )

        val employeeWork1 = EmployeeWorkModel(
            employeeId = employeeId1,
            workId = workId,
            startDate = LocalDate.now(),
            endDate = null
        )

        val employeeWork2 = EmployeeWorkModel(
            employeeId = employeeId2,
            workId = workId,
            startDate = LocalDate.now(),
            endDate = null
        )

        val work = WorkModel(
            id = workId,
            companyProviding = "Company A",
            cnpj = "12345678901234",
            workStatus = WorkStatus.ATIVA,
            address = addressModel,
            employeesWorks = listOf(employeeWork1, employeeWork2),
            toolsWorks = emptyList()
        )

        val employee1 = Employee(
            id = employeeId1,
            name = "João Silva",
            cpf = "12345678909",
            birthDate = LocalDate.of(1990, 5, 10),
            address = Address.of(addressModel),
            function = Function.of(functionModel),
            generalRegistration = "GR123",
            hourlyRate = 30.0,
            employeeStatus = EmployeeStatus.ATIVO,
            employeesWorks = emptyList(),
            employeeEpis = emptyList(),
            toolsEmployee = emptyList(),
            phones = "62 33252367"
        )

        every { workRepository.findById(workId) } returns work
        every { employeeRepository.findAllById(listOf(employeeId1)) } returns listOf(employee1)
        every { workRepository.save(any()) } answers { firstArg() }

        // Act
        val result = workServiceImpl.removeEmployeeFromWork(removeEmployeesWork)

        // Assert
        assertEquals(2, result.employeesWorks.size)
        assertEquals(endDate, result.employeesWorks[0].endDate) // Verifica se a data de término foi atualizada
        assertNull(result.employeesWorks[1].endDate) // Verifica se o outro funcionário não foi afetado
        verify(exactly = 1) { workRepository.findById(workId) }
        verify(exactly = 1) { employeeRepository.findAllById(listOf(employeeId1)) }
        verify(exactly = 1) { workRepository.save(any()) }
    }
    @Test
    fun `removeEmployeeFromWork should not update endDate if employee is not in work`() {
        // Arrange
        val workId = UUID.randomUUID()
        val employeeId1 = UUID.randomUUID()
        val employeeId2 = UUID.randomUUID()
        val endDate = LocalDate.now()

        val removeEmployeesWork = RemoveEmployeesWork(
            workId = workId,
            employees = listOf(employeeId1),
            endDate = endDate
        )

        val employeeWork2 = EmployeeWorkModel(
            employeeId = employeeId2,
            workId = workId,
            startDate = LocalDate.now(),
            endDate = null
        )

        val work = WorkModel(
            id = workId,
            companyProviding = "Company A",
            cnpj = "12345678901234",
            workStatus = WorkStatus.ATIVA,
            address = addressModel,
            employeesWorks = listOf(employeeWork2), // Apenas employeeId2 está no trabalho
            toolsWorks = emptyList()
        )

        val employee1 = Employee(
            id = employeeId1,
            name = "João Silva",
            cpf = "12345678909",
            birthDate = LocalDate.of(1990, 5, 10),
            address = Address.of(addressModel),
            function = Function.of(functionModel),
            generalRegistration = "GR123",
            hourlyRate = 30.0,
            employeeStatus = EmployeeStatus.ATIVO,
            employeesWorks = emptyList(),
            employeeEpis = emptyList(),
            toolsEmployee = emptyList(),
            phones = "62 33252367"
        )

        every { workRepository.findById(workId) } returns work
        every { employeeRepository.findAllById(listOf(employeeId1)) } returns listOf(employee1)
        every { workRepository.save(any()) } answers { firstArg() }

        // Act
        val result = workServiceImpl.removeEmployeeFromWork(removeEmployeesWork)

        // Assert
        assertEquals(1, result.employeesWorks.size)
        assertNull(result.employeesWorks[0].endDate) // Verifica se a data de término não foi atualizada
        verify(exactly = 1) { workRepository.findById(workId) }
        verify(exactly = 1) { employeeRepository.findAllById(listOf(employeeId1)) }
        verify(exactly = 1) { workRepository.save(any()) }
    }

}