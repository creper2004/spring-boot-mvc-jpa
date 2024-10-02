package ru.gorohov.springbootmvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.gorohov.springbootmvc.model.UserDto;
import ru.gorohov.springbootmvc.service.UserService;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldCreateNewUser() throws Exception {
        var userDto = new UserDto(null, "Pavel", "test@example.com", 25, List.of());
        String newUserJson = objectMapper.writeValueAsString(userDto);

        var jsonResponse = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var userDtoResponse = objectMapper.readValue(jsonResponse, UserDto.class);
        Assertions.assertEquals(userDto.getName(), userDtoResponse.getName());
        Assertions.assertEquals(userDto.getAge(), userDtoResponse.getAge());
        Assertions.assertEquals(userDto.getEmail(), userDtoResponse.getEmail());
        Assertions.assertEquals(userDto.getPets(), userDtoResponse.getPets());
        Assertions.assertDoesNotThrow(() -> userService.findUserById(userDtoResponse.getId()));
    }

}
