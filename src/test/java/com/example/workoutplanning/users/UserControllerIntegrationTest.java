package com.example.workoutplanning.users;

import com.example.workoutplanning.users.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.HashMap;
import java.util.Map;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void testCreateUserEndpoint() throws Exception {
        when(userService.createUser(anyString(), anyString(), anyString())).thenReturn("User created successfully");
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", "testUser");
        requestBody.put("email", "test@example.com");
        requestBody.put("password", "testPassword");

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().string("User created successfully"));
    }

    @Test
    public void testCreateUserEndpoint_UserAlreadyExists() throws Exception {
        when(userService.createUser(anyString(), anyString(), anyString())).thenThrow(new RuntimeException("User already exists"));
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", "testUser");
        requestBody.put("email", "test@example.com");
        requestBody.put("password", "testPassword");

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User already exists"));
    }

    @Test
    public void testCreateUserEndpoint_EmailAlreadyExists() throws Exception {
        when(userService.createUser(anyString(), anyString(), anyString())).thenThrow(new RuntimeException("Email already exists"));
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", "testUser");
        requestBody.put("email", "test@example.com");
        requestBody.put("password", "testPassword");

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Email already exists"));
    }

    @Test
    public void testLoginUserEndpoint() throws Exception {
        when(userService.loginUser(anyString(), anyString())).thenReturn(1);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", "testUser");
        requestBody.put("password", "testPassword");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    public void testLoginUserEndpoint_UserNotFound() throws Exception {
        when(userService.loginUser(anyString(), anyString())).thenThrow(new RuntimeException("User not found"));
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", "testUser");
        requestBody.put("password", "testPassword");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User not found"));
    }

    @Test
    public void testLoginUserEndpoint_WrongPassword() throws Exception {
        when(userService.loginUser(anyString(), anyString())).thenThrow(new RuntimeException("Wrong password"));
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", "testUser");
        requestBody.put("password", "testPassword");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Wrong password"));
    }
}
