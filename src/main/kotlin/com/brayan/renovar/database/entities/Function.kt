package com.brayan.renovar.database.entities

import com.brayan.renovar.database.entities.Function
import com.brayan.renovar.models.FunctionModel
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "tb_funcao")
data class Function(
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    val id: UUID? = null,
    @Column(name = "funcao")
    val function: String,
    @Column(name = "descricao")
    val description: String,
    @Column(name = "creation_date")
    @CreationTimestamp
    val creationDate: LocalDateTime?,
    @Column(name = "update_date")
    @UpdateTimestamp
    val updateDate: LocalDateTime?
) {
    fun toFunctionModel() = FunctionModel(
        id = id,
        function = function,
        description = description,
        creationDate = creationDate,
        updateDate = updateDate
    )
    companion object {
        fun of(functionModel: FunctionModel) = Function(
            id = functionModel.id,
            function = functionModel.function,
            description = functionModel.description,
            creationDate = functionModel.creationDate,
            updateDate = functionModel.updateDate
        )
    }
}