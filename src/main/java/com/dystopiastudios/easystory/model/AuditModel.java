package com.dystopiastudios.easystory.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass // Clase abstracta que no tendra contraparte relacional y que es clase padre
@EntityListeners(AuditingEntityListener.class)
// Los atributos contenidos en AuditModel van a relacionarse con propiedades de pistas de auditorias con campos que se llenan automaticamente
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
// No incluya o no pida los valores en el formato JSon
@Getter // Lombok
@Setter // Lombok
// Esta clase NO va a tener instancias
public abstract class AuditModel implements Serializable {
    // Podra convertirse a formato serializable
    // es decir almacenable
    @Column(nullable = false, updatable = false) // Columna relacional que le corresponde
    @Temporal(TemporalType.TIMESTAMP) // Tipo de dato Fecha = TIMESTAMP
    @CreatedDate // Valor de creacion al momento de generar
    private Date createdAt; //lowerCamelCase

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;
}
