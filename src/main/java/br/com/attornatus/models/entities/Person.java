package br.com.attornatus.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
@Entity
@DynamicUpdate
@Table(name = "TB_PERSONS")
public class Person implements Serializable {

    private static final long serialVersionUID = 1l;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(nullable = false, length = 128)
    private final String name;

    @Column(columnDefinition = "DATE")
    private final LocalDate birth;

    private Person() {
        this.id = null;
        this.name = null;
        this.birth = null;
    }

}
