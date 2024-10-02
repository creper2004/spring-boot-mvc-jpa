package ru.gorohov.springbootmvc.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.gorohov.springbootmvc.entity.User;
import ru.gorohov.springbootmvc.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service

public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        if (user.getId() != null)  {
            throw new IllegalArgumentException("Id for user should not be provided");
        }
        if (userRepository.findUserByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email: %s already taken".formatted(user.getEmail()));
        }
        if (user.getPets() != null && !user.getPets().isEmpty()) {
            throw new IllegalArgumentException("Pets must be empty while creating user");
        }
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(User user) {
        if (user.getId() == null)  {
            throw new IllegalArgumentException("Id for user is null");
        }
        if (findUserById(user.getId()).isEmpty()) {
            throw new NoSuchElementException("User does not exist with id: " + user.getId());
        }
        if (userRepository.findUserByEmail(user.getEmail()) != null)
        {
            if (userRepository.findUserByEmail(user.getEmail()).getId() != user.getId()){
                throw new IllegalArgumentException("Email: %s already taken".formatted(user.getEmail()));
            }
        }
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUserById(Long id) {
        if (findUserById(id).isEmpty()) {
            throw new NoSuchElementException("User does not exist with id: " + id);
        }
        userRepository.deleteById(id);
    }

    public Optional<User> findUserById(Long id) {
        return Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User does not exist")));
    }
}
