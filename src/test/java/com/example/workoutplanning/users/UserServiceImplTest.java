package com.example.workoutplanning.users;

import com.example.workoutplanning.achievements.services.AchievementService;
import com.example.workoutplanning.users.model.User;
import com.example.workoutplanning.users.repositories.UserRepository;
import com.example.workoutplanning.users.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AchievementService achievementService;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        // Arrange
        String username = "testUser";
        String email = "test@example.com";
        String password = "password123";

        when(userRepository.findByUsername(username)).thenReturn(null);
        when(userRepository.findByEmail(email)).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(new User());
        doNothing().when(achievementService).initAchievementsDataForUser(anyInt());

        // Act
        String result = userService.createUser(username, email, password);

        // Assert
        assertEquals("User created!", result);
        verify(userRepository, times(1)).save(any(User.class));
        verify(achievementService, times(1)).initAchievementsDataForUser(anyInt());
    }

    @Test
    void testCreateUserUsernameExists() {
        // Arrange
        String existingUsername = "existingUser";
        String email = "test@example.com";
        String password = "password123";

        when(userRepository.findByUsername(existingUsername)).thenReturn(new User());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.createUser(existingUsername, email, password));
        verify(userRepository, never()).save(any(User.class));
        verify(achievementService, never()).initAchievementsDataForUser(anyInt());
    }

    @Test
    void testCreateUserEmailExists() {
        // Arrange
        String username = "testUser";
        String existingEmail = "existing@example.com";
        String password = "password123";

        when(userRepository.findByUsername(username)).thenReturn(null);
        when(userRepository.findByEmail(existingEmail)).thenReturn(new User());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.createUser(username, existingEmail, password));
        verify(userRepository, never()).save(any(User.class));
        verify(achievementService, never()).initAchievementsDataForUser(anyInt());
    }

    @Test
    void testLoginUser() {
        // Arrange
        String username = "testUser";
        String password = "password123";
        User user = new User();
        user.setId(1);
        user.setPassword(password);

        when(userRepository.findByUsername(username)).thenReturn(user);

        // Act
        Integer result = userService.loginUser(username, password);

        // Assert
        assertEquals(user.getId(), result);
    }

    @Test
    void testLoginUserNotFound() {
        // Arrange
        String nonExistingUsername = "nonExistingUser";
        String password = "password123";

        when(userRepository.findByUsername(nonExistingUsername)).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.loginUser(nonExistingUsername, password));
    }

    @Test
    void testLoginUserWrongPassword() {
        // Arrange
        String username = "testUser";
        String correctPassword = "password123";
        String wrongPassword = "wrongPassword";
        User user = new User();
        user.setPassword(correctPassword);

        when(userRepository.findByUsername(username)).thenReturn(user);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.loginUser(username, wrongPassword));
    }
}
