package com.example.workoutplanning.users;

import com.example.workoutplanning.users.services.UserDataService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.HashMap;
import java.util.Map;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class UserDataControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserDataService userDataService;

    @Test
    public void testCreateUserDataEndpoint() throws Exception {
        // Request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("weight", "80");
        requestBody.put("height", "190");
        requestBody.put("age", "25");

        // Request headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", "1");

        // Performing the request and asserting the response
        mockMvc.perform(post("/userdata")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
    }


    @Test
    public void testGetUserDataEndpoint() throws Exception {
        when(userDataService.getUserData(anyInt())).thenReturn("User data retrieved successfully!");

        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", "1");

        mockMvc.perform(get("/userdata").header("user_id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(headers)))
                .andExpect(status().isOk())
                .andExpect(content().string("User data retrieved successfully!"));
    }

    @Test
    public void testGetUserDataEndpoint_UserNotFound() throws Exception {
        when(userDataService.getUserData(anyInt())).thenThrow(new RuntimeException("User not found"));

        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", "1");

        mockMvc.perform(get("/userdata").header("user_id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(headers)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User not found"));
    }

    @Test
    public void testUpdateUserDataEndpoint() throws Exception {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("weight", "80");
        requestBody.put("height", "190");
        requestBody.put("age", "25");

        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", "1");

        mockMvc.perform(put("/userdata").header("user_id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUserDataEndpoint() throws Exception {
        when(userDataService.deleteUserData(anyInt())).thenReturn("User data deleted successfully!");

        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", "1");

        mockMvc.perform(delete("/userdata").header("user_id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User data deleted successfully!"));
    }

    @Test
    public void testDeleteUserDataEndpoint_UserNotFound() throws Exception {
        when(userDataService.deleteUserData(anyInt())).thenThrow(new RuntimeException("User not found"));

        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", "1");

        mockMvc.perform(delete("/userdata").header("user_id", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User not found"));
    }


}

