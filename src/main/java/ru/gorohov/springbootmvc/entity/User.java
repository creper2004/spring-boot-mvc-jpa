package ru.gorohov.springbootmvc.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.gorohov.springbootmvc.model.PetDto;

import java.util.List;

@Entity
@Table(name="user_table")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;


    @Column(unique=true)
    private String email;

    private Integer age;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pet> pets;


    public User(Long id, String name, String email, Integer age, List<Pet> pets) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.pets = pets;
    }

    public User() {

    }
}
