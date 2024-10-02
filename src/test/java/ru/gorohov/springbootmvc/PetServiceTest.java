package ru.gorohov.springbootmvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.gorohov.springbootmvc.entity.User;
import ru.gorohov.springbootmvc.model.PetDto;
import ru.gorohov.springbootmvc.model.UserDto;
import ru.gorohov.springbootmvc.service.PetService;
import ru.gorohov.springbootmvc.service.UserService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class PetServiceTest {
    @Autowired
    private PetService petService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @PersistenceContext
    private EntityManager entityManager;


    @Test
    public void shouldCreateNewPet() throws Exception {
        var user = userService.createUser(new User(
                null,
                "Pavel",
                "mail@mail.ru",
                25,
                List.of()
        ));

        var petDto = new PetDto(null, "Vasya", user.getId());
        String newPetDto = objectMapper.writeValueAsString(petDto);

        var jsonResponse = mockMvc.perform(post("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newPetDto))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var petDtoResponse = objectMapper.readValue(jsonResponse, PetDto.class);

        Assertions.assertEquals(petDto.getName(), petDtoResponse.getName());
        Assertions.assertEquals(petDto.getUserId(), petDtoResponse.getUserId());
        Assertions.assertNotNull(petDtoResponse.getId());

        Assertions.assertDoesNotThrow(() -> petService.findPetById(petDtoResponse.getId()));

        entityManager.flush();
        entityManager.clear();


        var userWithPet = userService.findUserById(user.getId());
        Assertions.assertEquals(1, userWithPet.get().getPets().size());
        Assertions.assertEquals(petDtoResponse.getId(), userWithPet.get().getPets().get(0).getId());
    }
}

