package ru.gorohov.springbootmvc.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.gorohov.springbootmvc.entity.Pet;
import ru.gorohov.springbootmvc.repository.PetRepository;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public Pet createPet(Pet pet) {
        if (pet.getId() != null) {
            throw new IllegalArgumentException("Pet id should not be provided");
        }

        return petRepository.save(pet);
    }

    @Transactional
    public Pet updatePet(Pet pet) {
        if (pet.getId() == null) {
            throw new IllegalArgumentException("Pet id is null");
        }
        if (!petRepository.existsById(pet.getId())) {
            throw new IllegalArgumentException("Pet does not exist");
        }
        return petRepository.save(pet);
    }

    @Transactional
    public void deletePetById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Pet id should not be null");
        }
        if (findPetById(id) == null) {
            throw new IllegalArgumentException("Pet does not exist with id " + id);
        }

        petRepository.deletePetById(id);
    }
    public Pet findPetById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Pet not found with id " + id));
    }
}
