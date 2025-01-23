package com.brayan.renovar.database.entities

import com.brayan.renovar.models.AddressModel
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "tb_endereco")
data class Address(
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    val id: UUID? = null,
    @Column(name = "rua")
    val street: String? = null,
    @Column(name = "numero")
    val number: String? = null,
    @Column(name = "complemento")
    val complement: String? = null,
    @Column(name = "bairro")
    val neighborhood: String? = null,
    @Column(name = "cidade")
    val city: String,
    @Column(name = "estado")
    val state: String,
    @Column(name = "cep")
    val zipCode: String,
    @Column(name = "creation_date")
    @CreationTimestamp
    val creationDate: LocalDateTime? = null,
    @Column(name = "update_date")
    @UpdateTimestamp
    val updateDate: LocalDateTime? = null
){
    fun toAddressModel() = AddressModel(
        id = id,
        street = street,
        number = number,
        complement = complement,
        neighborhood = neighborhood,
        city = city,
        state = state,
        zipCode = zipCode,
        creationDate = creationDate,
        updateDate = updateDate
    )
    companion object{
        fun of(addressModel: AddressModel) = Address(
            id = addressModel.id,
            street = addressModel.street,
            number = addressModel.number,
            complement = addressModel.complement,
            neighborhood = addressModel.neighborhood,
            city = addressModel.city,
            state = addressModel.state,
            zipCode = addressModel.zipCode,
            creationDate = addressModel.creationDate,
            updateDate = addressModel.updateDate
        )
    }
}
