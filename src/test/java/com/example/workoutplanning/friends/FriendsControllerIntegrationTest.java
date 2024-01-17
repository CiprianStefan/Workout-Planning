package com.example.workoutplanning.friends;

import com.example.workoutplanning.friends.services.FriendsService;
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
public class FriendsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FriendsService friendsService;

    @Test
    public void testAddFriendEndpoint() throws Exception {
        // Mocking the service response
        when(friendsService.addFriend(anyInt(), anyInt())).thenReturn("Friend added successfully!");

        // Request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("friend_id", "1");

        // Request headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", "1");

        mockMvc.perform(post("/friends")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().string("Friend added successfully!"));
    }

    @Test
    public void testRemoveFriendEndpoint() throws Exception {
        // Mocking the service response
        when(friendsService.removeFriend(anyInt(), anyInt())).thenReturn("Friend removed successfully!");

        // Request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("friend_id", "1");

        // Request headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", "1");

        mockMvc.perform(delete("/friends")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().string("Friend removed successfully!"));
    }

    @Test
    public void testGetFriendsEndpoint() throws Exception {
        // Mocking the service response
        when(friendsService.getFriendsList(anyInt())).thenReturn("Friends retrieved successfully!");

        // Request headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", "1");

        mockMvc.perform(get("/friends")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(headers)))
                .andExpect(status().isOk())
                .andExpect(content().string("Friends retrieved successfully!"));
    }
}
