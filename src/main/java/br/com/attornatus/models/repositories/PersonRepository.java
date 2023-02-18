package br.com.attornatus.models.repositories;

import br.com.attornatus.models.entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Page<Person> findByNameContains(String name, Pageable pageable);

    Page<Person> findByBirth(LocalDate birth, Pageable pageable);

    Page<Person> findByBirthBetween(LocalDate start, LocalDate end, Pageable pageable);

}
