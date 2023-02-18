package br.com.attornatus.models.repositories;

import br.com.attornatus.models.entities.Address;
import br.com.attornatus.models.enums.AddressRelevanceLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT a FROM Address a WHERE a.person.id = ?1")
    Page<Address> findByPeopleId(@Param("personId") Long personId, Pageable pageable);

    @Query("SELECT a FROM Address a WHERE a.person.id = ?1 AND a.relevanceLevel = 'HIGH'")
    Address findHighRelevanceLevelByPeopleId(@Param("personId") Long personId);

    Page<Address> findByRelevanceLevel(AddressRelevanceLevel relevanceLevel, Pageable pageable);

    Page<Address> findByPublicPlaceContains(String plublicPlace, Pageable pageable);

    Page<Address> findByCityContains(String city, Pageable pageable);

    Page<Address> findByZipCode(String zipCode, Pageable pageable);

}
