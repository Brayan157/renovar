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
import java.time.LocalDateTime

@Entity
@Table(name = "tb_tools_work")
data class ToolsWork(
    @EmbeddedId
    private val id: ToolsWorkId = ToolsWorkId(),

    @ManyToMany
    @MapsId("toolsId")
    @JoinColumn(name = "tools_id")
    val tool: Tool,

    @ManyToMany
    @MapsId("workId")
    @JoinColumn(name = "work_id")
    val work: Work,

    @Column(name = "motivo")
    val reason:String,
    @Column(name = "data_entrada")
    val entryDate:String,
    @Column(name = "data_saida")
    val exitDate:String,
    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    @JsonManagedReference
    val responsible:Employee? = null,
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
        responsible = responsible?.toEmployeeModel(),
        creationDate = creationDate,
        updateDate = updateDate
    )
}
