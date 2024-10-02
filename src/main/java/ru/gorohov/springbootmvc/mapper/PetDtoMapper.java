package ru.gorohov.springbootmvc.mapper;

import org.springframework.stereotype.Component;
import ru.gorohov.springbootmvc.entity.Pet;
import ru.gorohov.springbootmvc.entity.User;
import ru.gorohov.springbootmvc.model.PetDto;
import ru.gorohov.springbootmvc.service.UserService;

@Component
public class PetDtoMapper {

    private final UserService userService;

    public PetDtoMapper(UserService userService) {
        this.userService = userService;
    }

    public PetDto toDto(Pet pet) {
        return new PetDto(
                pet.getId(),
                pet.getName(),
                pet.getUser().getId()
        );
    }

    public Pet toPet(PetDto petDto) {
        User user = userService.findUserById(petDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return new Pet(
                petDto.getId(),
                petDto.getName(),
                user
        );
    }
}
