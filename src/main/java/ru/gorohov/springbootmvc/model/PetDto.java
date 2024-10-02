package ru.gorohov.springbootmvc.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor // нужно для десериализации JSON
public class PetDto {
    private Long id;

    @NotBlank(message = "Name of pet should not be empty")
    @Size(min = 2, max = 50, message = "Name of pet should contain from 2 to 50 symbols")
    private String name;

    private Long userId; // Здесь теперь только идентификатор пользователя
}
