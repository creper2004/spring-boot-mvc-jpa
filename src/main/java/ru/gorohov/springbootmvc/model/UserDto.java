package ru.gorohov.springbootmvc.model;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    @NotBlank(message = "Illegal format of user's name")
    @Size(min = 2, max = 100, message = "Name of user should contain from 2 to 100 symbols")
    private String name;

    @Email(message = "Illegal email format")
    private String email;

    @Min(value = 0, message = "Minimal age value - 0")
    @Max(value = 200, message = "Maximum age value - 200")
    private Integer age;

    private List<PetDto> pets = new ArrayList<>();;


}
