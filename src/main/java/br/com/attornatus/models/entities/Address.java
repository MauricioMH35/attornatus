package br.com.attornatus.models.entities;

import br.com.attornatus.models.enums.AddressRelevanceLevel;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
@Entity
@DynamicUpdate
@Table(name = "TB_ADDRESSES")
public class Address implements Serializable {

    private static final long serialVersionUID = 1l;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private final AddressRelevanceLevel relevanceLevel;

    @Column(nullable = false, length = 128)
    private final String publicPlace;

    @Column(nullable = false, length = 8)
    private final String zipCode;

    @Column(nullable = false, length = 6)
    private final Integer number;

    @Column(length = 64)
    private final String city;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    private Address() {
        this.id = null;
        this.relevanceLevel = null;
        this.publicPlace = null;
        this.zipCode = null;
        this.number = null;
        this.city = null;
        this.person = null;
    }

}
