package com.brayan.renovar.database.entities

import com.brayan.renovar.models.ToolsWorkModel
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "tb_tools_work")
data class ToolsWork(
    @EmbeddedId
    private val id: ToolsWorkId = ToolsWorkId(),

    @ManyToOne
    @MapsId("toolsId")
    @JoinColumn(name = "ferramenta_id")
    val tool: Tool,

    @ManyToOne
    @MapsId("workId")
    @JoinColumn(name = "obra_id")
    val work: Work,

    @Column(name = "motivo")
    val reason:String,
    @Column(name = "data_entrada")
    val entryDate:LocalDate,
    @Column(name = "data_saida")
    val exitDate:LocalDate? = null,
    @Column(name = "creation_date")
    @CreationTimestamp
    val creationDate:LocalDateTime? = null,
    @Column(name = "update_date")
    @UpdateTimestamp
    val updateDate:LocalDateTime? = null
){
    fun toToolsWorkModel() = ToolsWorkModel(
        toolsId = tool.id!!,
        workId = work.id!!,
        reason = reason,
        entryDate = entryDate,
        exitDate = exitDate,
        creationDate = creationDate,
        updateDate = updateDate
    )
}
