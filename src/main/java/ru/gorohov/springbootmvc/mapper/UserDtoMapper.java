package ru.gorohov.springbootmvc.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gorohov.springbootmvc.entity.User;
import ru.gorohov.springbootmvc.model.UserDto;

@Component
@AllArgsConstructor
public class UserDtoMapper {

    private PetDtoMapper petDtoMapper;

    public User toUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail(),
                userDto.getAge(),
                userDto.getPets().stream().map(petDto -> petDtoMapper.toPet(petDto)).toList()
        );
    }

    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge(),
                user.getPets().stream().map(pet -> petDtoMapper.toDto(pet)).toList()
        );
    }
}
