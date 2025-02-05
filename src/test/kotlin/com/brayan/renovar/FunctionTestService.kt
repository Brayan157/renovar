package com.brayan.renovar

import com.brayan.renovar.api.request.FunctionRequest
import com.brayan.renovar.database.entities.Function
import com.brayan.renovar.database.repositories.implementations.EmployeeRepositoryImpl
import com.brayan.renovar.database.repositories.interfaces.EPIRepository
import com.brayan.renovar.database.repositories.interfaces.EmployeeRepository
import com.brayan.renovar.database.repositories.interfaces.ToolRepository
import com.brayan.renovar.database.repositories.interfaces.WorkRepository
import com.brayan.renovar.models.FunctionModel
import com.brayan.renovar.services.implementations.EmployeeServiceImpl
import com.brayan.renovar.services.interfaces.EmployeeService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import java.util.UUID
import kotlin.test.assertNotNull

class FunctionTestService {

    private val employeeRepository = mockk<EmployeeRepository>()
    private val epiRepository = mockk<EPIRepository>()
    private val toolRepository = mockk<ToolRepository>()
    private val workRepository = mockk<WorkRepository>()
    private val functionServiceImpl = EmployeeServiceImpl(employeeRepository, epiRepository, toolRepository)

    private val functionModel = FunctionModel(
        function = "function 2",
        description = "description test"
    )
    private val functionRequest = FunctionRequest(
        name = "function 2",
        description = "description test"
    )

    @Test
    fun `createFunction should return a FunctionModel`() {
        // Arrange
        every { employeeRepository.save(any()) } returns functionModel

        // Act
        val createFunction = functionServiceImpl.createFunction(functionRequest)

        // Assert
        assertEquals(functionModel, createFunction)
        assertEquals(functionRequest.name, createFunction.function)

        // Verify
        verify(exactly = 1) { employeeRepository.save(any()) }
    }
    @Test
    fun `getFunctionById should return the correct FunctionModel`() {
        // Arrange
        val functionId = UUID.randomUUID()
        every { employeeRepository.findById(functionId) } returns functionModel

        // Act
        val retrievedFunction = functionServiceImpl.getFunctionById(functionId)

        // Assert
        assertNotNull(retrievedFunction)
        assertEquals(functionModel, retrievedFunction)
        assertEquals(functionModel.function, retrievedFunction.function)

        // Verify
        verify(exactly = 1) { employeeRepository.findById(functionId) }
    }
    @Test
    fun `getFunctionByName should return a list of FunctionModels`() {
        // Arrange
        val functionName = "function 2"
        val functions = listOf(functionModel)
        every { employeeRepository.findByName(functionName) } returns functions

        // Act
        val retrievedFunctions = functionServiceImpl.getFunctionByName(functionName)

        // Assert
        assertNotNull(retrievedFunctions)
        assertFalse(retrievedFunctions.isEmpty())
        assertEquals(1, retrievedFunctions.size)
        assertEquals(functionModel, retrievedFunctions.first())

        // Verify
        verify(exactly = 1) { employeeRepository.findByName(functionName) }
    }
    @Test
    fun `getFunctions should return a list of all FunctionModels`() {
        // Arrange
        val functions = listOf(
            FunctionModel(function = "function 1", description = "description 1"),
            FunctionModel(function = "function 2", description = "description 2")
        )
        every { employeeRepository.findAll() } returns functions

        // Act
        val retrievedFunctions = functionServiceImpl.getFunctions()

        // Assert
        assertNotNull(retrievedFunctions)
        assertFalse(retrievedFunctions.isEmpty())
        assertEquals(2, retrievedFunctions.size)
        assertEquals("function 1", retrievedFunctions[0].function)
        assertEquals("function 2", retrievedFunctions[1].function)

        // Verify
        verify(exactly = 1) { employeeRepository.findAll() }
    }

}