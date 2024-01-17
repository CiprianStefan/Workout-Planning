package com.example.workoutplanning.users;

import com.example.workoutplanning.users.model.User;
import com.example.workoutplanning.users.model.UserData;
import com.example.workoutplanning.users.repositories.UserDataRepository;
import com.example.workoutplanning.users.repositories.UserRepository;
import com.example.workoutplanning.users.services.UserDataServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDataServiceImplTest {

    @Mock
    private UserDataRepository userDataRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDataServiceImpl userDataService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUserData() {
        // Arrange
        UserData userData = new UserData();
        userData.setUserid(1);

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(new User()));
        when(userDataRepository.findByUserid(1)).thenReturn(null);

        // Act
        String result = userDataService.createUserData(userData);

        // Assert
        assertNotNull(result);
        assertEquals(userData.toString(), result);

        // Verify
        verify(userRepository, times(1)).findById(1L);
        verify(userDataRepository, times(1)).findByUserid(1);
        verify(userDataRepository, times(1)).save(userData);
    }

    @Test
    void testGetUserData() {
        // Arrange
        int userId = 1;
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(new User()));
        when(userDataRepository.findByUserid(userId)).thenReturn(new UserData());

        // Act
        String result = userDataService.getUserData(userId);

        // Assert
        assertNotNull(result);

        // Verify
        verify(userDataRepository, times(1)).findByUserid(userId);
    }

    @Test
    void testGetUserData_UserNotFound() {
        // Arrange
        int userId = 1;
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> userDataService.getUserData(userId));

        // Verify
        verify(userRepository, times(1)).findById(1L);
        verifyNoInteractions(userDataRepository);
    }

    @Test
    void testUpdateUserData() {
        // Arrange
        UserData userData = new UserData();
        userData.setUserid(1);

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(new User()));
        when(userDataRepository.findByUserid(1)).thenReturn(userData);

        // Act
        String result = userDataService.updateUserData(userData);

        // Assert
        assertNotNull(result);
        assertEquals(userData.toString(), result);

        // Verify
        verify(userRepository, times(1)).findById(1L);
        verify(userDataRepository, times(1)).findByUserid(1);
        verify(userDataRepository, times(1)).updateUserDataRegister(1, userData.getWeight(), userData.getHeight(), userData.getAge());
    }

    @Test
    void testDeleteUserData() {
        // Arrange
        int userId = 1;
        UserData userData = new UserData();
        userData.setUserid(userId);

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(new User()));
        when(userDataRepository.findByUserid(userId)).thenReturn(userData);

        // Act
        String result = userDataService.deleteUserData(userId);

        // Assert
        assertNotNull(result);
        assertEquals("User data deleted!", result);

        // Verify
        verify(userRepository, times(1)).findById(1L);
        verify(userDataRepository, times(1)).findByUserid(userId);
        verify(userDataRepository, times(1)).delete(userData);
    }

    @Test
    void testCreateUserData_UserNotFound() {
        // Arrange
        UserData userData = new UserData();
        userData.setUserid(1);

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> userDataService.createUserData(userData));

        // Verify
        verify(userRepository, times(1)).findById(1L);
        verifyNoInteractions(userDataRepository);
    }

    @Test
    void testCreateUserData_UserDataAlreadyExists() {
        // Arrange
        UserData userData = new UserData();
        userData.setUserid(1);

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(new User()));
        when(userDataRepository.findByUserid(1)).thenReturn(new UserData());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> userDataService.createUserData(userData));

        // Verify
        verify(userRepository, times(1)).findById(1L);
        verify(userDataRepository, times(1)).findByUserid(1);
        verifyNoMoreInteractions(userDataRepository);
    }

    @Test
    void testUpdateUserData_UserNotFound() {
        // Arrange
        UserData userData = new UserData();
        userData.setUserid(1);

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> userDataService.updateUserData(userData));

        // Verify
        verify(userRepository, times(1)).findById(1L);
        verifyNoInteractions(userDataRepository);
    }

    @Test
    void testUpdateUserData_UserDataNotFound() {
        // Arrange
        UserData userData = new UserData();
        userData.setUserid(1);

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(new User()));
        when(userDataRepository.findByUserid(1)).thenReturn(null);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> userDataService.updateUserData(userData));

        // Verify
        verify(userRepository, times(1)).findById(1L);
        verify(userDataRepository, times(1)).findByUserid(1);
        verifyNoMoreInteractions(userDataRepository);
    }

    @Test
    void testDeleteUserData_UserNotFound() {
        // Arrange
        int userId = 1;

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> userDataService.deleteUserData(userId));

        // Verify
        verify(userRepository, times(1)).findById(1L);
        verifyNoInteractions(userDataRepository);
    }

    @Test
    void testDeleteUserData_UserDataNotFound() {
        // Arrange
        int userId = 1;

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(new User()));
        when(userDataRepository.findByUserid(userId)).thenReturn(null);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> userDataService.deleteUserData(userId));

        // Verify
        verify(userRepository, times(1)).findById(1L);
        verify(userDataRepository, times(1)).findByUserid(userId);
        verifyNoMoreInteractions(userDataRepository);
    }
}

