package br.com.attornatus.models.repositories;

import br.com.attornatus.models.entities.People;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PeopleRepository extends JpaRepository<People, Long> {

    Page<People> findByNameContains(String name, Pageable pageable);

    Page<People> findByBirth(LocalDate birth, Pageable pageable);

    Page<People> findByBirthBetween(LocalDate start, LocalDate end, Pageable pageable);

}
