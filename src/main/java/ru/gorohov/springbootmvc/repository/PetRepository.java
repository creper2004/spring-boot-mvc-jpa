package ru.gorohov.springbootmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gorohov.springbootmvc.entity.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    void deletePetById(Long id);

}
