package ru.gorohov.springbootmvc.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gorohov.springbootmvc.entity.Pet;
import ru.gorohov.springbootmvc.mapper.PetDtoMapper;
import ru.gorohov.springbootmvc.model.PetDto;
import ru.gorohov.springbootmvc.service.PetService;
import ru.gorohov.springbootmvc.service.UserService;

@RestController
@RequestMapping("/pets")
public class PetsController {
    private final PetService petService;
    private final PetDtoMapper petDtoMapper;
    private final UserService userService;

    public PetsController(PetService petService, PetDtoMapper petDtoMapper, UserService userService) {
        this.petService = petService;
        this.petDtoMapper = petDtoMapper;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<PetDto> createPet(@RequestBody @Valid PetDto petDto) {
        var pet = petDtoMapper.toPet(petDto);
        return new ResponseEntity<>(petDtoMapper.toDto(petService.createPet(pet)), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDto> getPetById(@PathVariable Long id) {
        var pet = petService.findPetById(id);
        return new ResponseEntity<>(petDtoMapper.toDto(pet), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDto> updatePet(@PathVariable Long id, @RequestBody @Valid PetDto petDto) {
        var pet = new Pet(
                id,
                petDto.getName(),
                userService.findUserById(petDto.getUserId()).get()
        );
        var updatedUser = petService.updatePet(pet);
        return new ResponseEntity<>(petDtoMapper.toDto(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.deletePetById(id);
        return ResponseEntity.ok().build();
    }

}