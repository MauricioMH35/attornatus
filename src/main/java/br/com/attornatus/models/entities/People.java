package br.com.attornatus.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
@Entity
@DynamicUpdate
@Table(name = "TB_PEOPLE")
public class People implements Serializable {

    private static final long serialVersionUID = 1l;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(nullable = false, length = 128)
    private final String name;

    @Column(columnDefinition = "DATE")
    private final LocalDate birth;

    @OneToMany(mappedBy = "people", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Address> addresses;

    private People() {
        this.id = null;
        this.name = null;
        this.birth = null;
        this.addresses = null;
    }

}
