package com.brayan.renovar

import com.brayan.renovar.database.repositories.interfaces.WorkRepository
import com.brayan.renovar.enum.WorkStatus
import com.brayan.renovar.models.AddressModel
import com.brayan.renovar.models.WorkModel
import com.brayan.renovar.services.implementations.WorkServiceImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.UUID

class WorkServiceTest {
    private val workRepository = mockk<WorkRepository>()
    private val workServiceImpl = WorkServiceImpl(workRepository)

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

}