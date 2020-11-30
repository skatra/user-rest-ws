package com.stevekatra.springbootrestws;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private MvcResult mvcResult;
    private User user;

    @BeforeEach
    void setUp() throws Exception {
        user = new User(null,"john.doe@gmail.com","John Doe");
        mvcResult = mockMvc.perform(post("/user").
                contentType(MediaType.APPLICATION_JSON).
                content(mapToJson(user))).andExpect(status().isCreated()).andReturn();
    }

    @Test
    void getUsers() throws Exception {
        mockMvc.perform(get("/user")).andExpect(status().isOk());
    }

    @Test
    void getUserById() throws Exception {
        mvcResult = mockMvc.perform(get("/user/"+1L)).andExpect(status().isOk()).andReturn();
        User output = mapFromJson(mvcResult.getResponse().getContentAsString(),User.class);
        assertEquals(output.getId(),1L);
    }

    @Test
    void updateUserById() throws Exception {
        User user = new User(1L,"john.doe@live.com","John Doe");
        mvcResult = mockMvc.perform(put("/user/"+1L).contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(user))).andExpect(status().isOk()).andReturn();

        User output = mapFromJson(mvcResult.getResponse().getContentAsString(),User.class);
        assertEquals(user.getEmail(), output.getEmail());
    }

    @Test
    void deleteUserById() throws Exception {
        mockMvc.perform(delete("/user/"+1L)).andExpect(status().isOk());
        mockMvc.perform(get("/user/"+1L)).andExpect(status().isNotFound());
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

}
