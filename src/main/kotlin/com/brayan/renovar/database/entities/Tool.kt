package com.brayan.renovar.database.entities

import com.brayan.renovar.enum.ToolStatus
import com.brayan.renovar.models.ToolModel
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "tb_ferramenta")
data class Tool(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    val id: UUID? = null,
    @Column(name = "nome")
    val name: String,
    @Column(name = "data_compra")
    val purchaseDate: String,
    @Column(name = "status")
    val toolStatus: ToolStatus,
    @Column(name = "valor_unitario")
    val unitValue: Double,
    @Column(name = "creation_date")
    val creationDate: LocalDateTime? = null,
    @Column(name = "update_date")
    val updateDate: LocalDateTime? = null,
    @OneToMany(mappedBy = "tool", cascade = [CascadeType.ALL], orphanRemoval = true)
    val toolsWorks: List<ToolsWork> = mutableListOf()
){
    fun toToolModel() = ToolModel(
        id = id,
        name = name,
        purchaseDate = purchaseDate,
        toolStatus = toolStatus,
        unitValue = unitValue,
        creationDate = creationDate,
        updateDate = updateDate,
        toolsWorks = toolsWorks.map { it.toToolsWorkModel() }
    )

    companion object{
        fun of(toolModel: ToolModel, work: Map<UUID, Work>): Tool{
            val tool = Tool(
                id = toolModel.id,
                name = toolModel.name,
                purchaseDate = toolModel.purchaseDate,
                toolStatus = toolModel.toolStatus,
                unitValue = toolModel.unitValue,
                creationDate = toolModel.creationDate,
                updateDate = toolModel.updateDate
            )
            val works = toolModel.toolsWorks.map { toolsWorkModel ->
                val work = work[toolsWorkModel.workId] ?: throw Exception("Work not found")
                ToolsWork(
                    id = ToolsWorkId(
                        toolsId = tool.id!!,
                        workId = work.id!!
                    ),
                    tool = tool,
                    work = work,
                    reason = toolsWorkModel.reason,
                    entryDate = toolsWorkModel.entryDate,
                    exitDate = toolsWorkModel.exitDate,
                    creationDate = toolsWorkModel.creationDate,
                    updateDate = toolsWorkModel.updateDate
                )
            }
            return tool.copy(toolsWorks = works)
        }
    }
}
