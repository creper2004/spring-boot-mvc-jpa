package ru.gorohov.springbootmvc.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gorohov.springbootmvc.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    void deleteById(@NotNull Long id);
    User findUserByEmail(String email);

}
