package com.brayan.renovar.database.entities
import com.brayan.renovar.enum.WorkStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "tb_obra")
data class Work(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    val id: UUID? = null,
    @Column(name = "empresa_prestante")
    val companyProviding:String,
    @Column(name = "cnpj")
    val cnpj:String,
    @Column(name = "status")
    val workStatus: WorkStatus,
    @Column(name = "creation_date")
    val creationDate: LocalDateTime?,
    @Column(name = "update_date")
    val updateDate: LocalDateTime?
)
