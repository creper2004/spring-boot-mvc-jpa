package ru.gorohov.springbootmvc.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gorohov.springbootmvc.entity.User;
import ru.gorohov.springbootmvc.mapper.PetDtoMapper;
import ru.gorohov.springbootmvc.mapper.UserDtoMapper;
import ru.gorohov.springbootmvc.model.UserDto;
import ru.gorohov.springbootmvc.service.UserService;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;
    private final UserDtoMapper userDtoMapper;
    private final PetDtoMapper petDtoMapper;


    public UsersController(UserService userService, UserDtoMapper userDtoMapper, PetDtoMapper petDtoMapper) {
        this.userService = userService;
        this.userDtoMapper = userDtoMapper;
        this.petDtoMapper = petDtoMapper;
    }


    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) {
        var user = userDtoMapper.toUser(userDto);
        return new ResponseEntity<>(userDtoMapper.toDto(userService.createUser(user)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Long id) {
        var userToUpdate = new User(
                id,
                userDto.getName(),
                userDto.getEmail(),
                userDto.getAge(),
                userDto.getPets().stream().map(petDtoMapper::toPet).toList()
        );
        var updatedUser = userService.updateUser(userToUpdate);
        return new ResponseEntity<>(userDtoMapper.toDto(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(userDtoMapper.toDto(userService.findUserById(id).get()), HttpStatus.OK);
    }

}
