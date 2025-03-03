package database.repository

import com.brayan.renovar.api.request.AddEpiToEmployeeRequest
import com.brayan.renovar.api.request.ReturnEpiToEmployeeRequest
import com.brayan.renovar.database.entities.CreationDate
import com.brayan.renovar.database.entities.EPI
import com.brayan.renovar.database.entities.Employee
import com.brayan.renovar.database.entities.EmployeeEPI
import com.brayan.renovar.database.entities.EmployeeEPIId
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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.UUID

class EmployeeEpiTest {

}