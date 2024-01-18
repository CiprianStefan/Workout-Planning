package com.example.workoutplanning.friends;

import com.example.workoutplanning.friends.model.Friend;
import com.example.workoutplanning.friends.repositories.FriendsRepository;
import com.example.workoutplanning.friends.services.FriendsServiceImpl;
import com.example.workoutplanning.users.model.User;
import com.example.workoutplanning.users.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FriendServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private FriendsRepository friendsRepository;

    @InjectMocks
    private FriendsServiceImpl friendsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addFriend_Success() {
        // Arrange
        int userId = 1;
        int friendId = 2;
        User mockUser = new User(userId, "testUser", "1", "testPassword");
        User mockFriend = new User(friendId, "testFriend", "2", "testPassword");

        when(userRepository.findById((long) userId)).thenReturn(Optional.of(mockUser));
        when(userRepository.findById((long) friendId)).thenReturn(Optional.of(mockFriend));
        when(friendsRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        String result = friendsService.addFriend((int) userId, (int) friendId);

        // Assert
        assertEquals("Friend added!", result);
        verify(userRepository, times(1)).findById((long) userId);
        verify(userRepository, times(1)).findById((long) friendId);
        verify(friendsRepository, times(1)).findAll();
        verify(friendsRepository, times(1)).save(any(Friend.class));


    }

    @Test
    void addFriend_UserNotFound() {
        // Arrange
        int userId = 1;
        int friendId = 2;
        User mockFriend = new User(friendId, "testFriend", "2", "testPassword");

        when(userRepository.findById((long) userId)).thenReturn(Optional.empty());
        when(userRepository.findById((long) friendId)).thenReturn(Optional.of(mockFriend));

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            friendsService.addFriend((int) userId, (int) friendId);
        });

        // Assert
        assertEquals("User not found!", exception.getMessage());
        verify(userRepository, times(1)).findById((long) userId);
        verify(userRepository, times(0)).findById((long) friendId);
        verify(friendsRepository, times(0)).findAll();
        verify(friendsRepository, times(0)).save(any(Friend.class));
    }

    @Test
    void addFriend_FriendNotFound() {
        // Arrange
        int userId = 1;
        int friendId = 2;
        User mockUser = new User(userId, "testUser", "1", "testPassword");

        when(userRepository.findById((long) userId)).thenReturn(Optional.of(mockUser));
        when(userRepository.findById((long) friendId)).thenReturn(Optional.empty());

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            friendsService.addFriend((int) userId, (int) friendId);
        });

        // Assert
        assertEquals("Friend not found!", exception.getMessage());
        verify(userRepository, times(1)).findById((long) userId);
        verify(userRepository, times(1)).findById((long) friendId);
        verify(friendsRepository, times(0)).findAll();
        verify(friendsRepository, times(0)).save(any(Friend.class));
    }

    @Test
    void addFriend_FriendAlreadyAdded() {
        // Arrange
        int userId = 1;
        int friendId = 2;
        User mockUser = new User(userId, "testUser", "1", "testPassword");
        User mockFriend = new User(friendId, "testFriend", "2", "testPassword");
        ArrayList<Friend> friends = new ArrayList<>();
        friends.add(new Friend(userId, friendId));

        when(userRepository.findById((long) userId)).thenReturn(Optional.of(mockUser));
        when(userRepository.findById((long) friendId)).thenReturn(Optional.of(mockFriend));
        when(friendsRepository.findAll()).thenReturn(friends);

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            friendsService.addFriend((int) userId, (int) friendId);
        });

        // Assert
        assertEquals("Friend already added!", exception.getMessage());
        verify(userRepository, times(1)).findById((long) userId);
        verify(userRepository, times(1)).findById((long) friendId);
        verify(friendsRepository, times(1)).findAll();
        verify(friendsRepository, times(0)).save(any(Friend.class));
    }

    @Test
    void removeFriend_Success() {
        // Arrange
        int userId = 1;
        int friendId = 2;
        User mockUser = new User(userId, "testUser", "1", "testPassword");
        User mockFriend = new User(friendId, "testFriend", "2", "testPassword");

        when(userRepository.findById((long) userId)).thenReturn(Optional.of(mockUser));
        when(userRepository.findById((long) friendId)).thenReturn(Optional.of(mockFriend));

        // Act
        String result = friendsService.removeFriend((int) userId, (int) friendId);

        // Assert
        assertEquals("Friend deleted!", result);
        verify(userRepository, times(1)).findById((long) userId);
        verify(userRepository, times(1)).findById((long) friendId);
        verify(friendsRepository, times(1)).deleteFriend(userId, friendId);
    }

    @Test
    void removeFriend_UserNotFound() {
        // Arrange
        int userId = 1;
        int friendId = 2;
        User mockFriend = new User(friendId, "testFriend", "2", "testPassword");

        when(userRepository.findById((long) userId)).thenReturn(Optional.empty());
        when(userRepository.findById((long) friendId)).thenReturn(Optional.of(mockFriend));

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            friendsService.removeFriend((int) userId, (int) friendId);
        });

        // Assert
        assertEquals("User not found!", exception.getMessage());
        verify(userRepository, times(1)).findById((long) userId);
        verify(userRepository, times(0)).findById((long) friendId);
        verify(friendsRepository, times(0)).deleteFriend(userId, friendId);
    }

    @Test
    void removeFriend_FriendNotFound() {
        // Arrange
        int userId = 1;
        int friendId = 2;
        User mockUser = new User(userId, "testUser", "1", "testPassword");

        when(userRepository.findById((long) userId)).thenReturn(Optional.of(mockUser));
        when(userRepository.findById((long) friendId)).thenReturn(Optional.empty());

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            friendsService.removeFriend((int) userId, (int) friendId);
        });

        // Assert
        assertEquals("Friend not found!", exception.getMessage());
        verify(userRepository, times(1)).findById((long) userId);
        verify(userRepository, times(1)).findById((long) friendId);
        verify(friendsRepository, times(0)).deleteFriend(userId, friendId);
    }

    @Test
    void getFriendsList_Success() {
        // Arrange
        int userId = 1;
        User mockUser = new User(userId, "testUser", "1", "testPassword");
        ArrayList<Friend> friends = new ArrayList<>();
        friends.add(new Friend(userId, 2));
        friends.add(new Friend(userId, 3));

        when(userRepository.findById((long) userId)).thenReturn(Optional.of(mockUser));
        when(friendsRepository.findAll()).thenReturn(friends);

        // Act
        String result = friendsService.getFriendsList((int) userId);

        // Assert
        assertEquals(friends.toString(), result);
        verify(userRepository, times(1)).findById((long) userId);
        verify(friendsRepository, times(1)).findAll();
    }

    @Test
    void getFriendsList_UserNotFound() {
        // Arrange
        int userId = 1;

        when(userRepository.findById((long) userId)).thenReturn(Optional.empty());

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            friendsService.getFriendsList((int) userId);
        });

        // Assert
        assertEquals("User not found!", exception.getMessage());
        verify(userRepository, times(1)).findById((long) userId);
        verify(friendsRepository, times(0)).findAll();
    }

    @Test
    void getFriendsList_NoFriends() {
        // Arrange
        int userId = 1;
        User mockUser = new User(userId, "testUser", "1", "testPassword");

        when(userRepository.findById((long) userId)).thenReturn(Optional.of(mockUser));
        when(friendsRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        String result = friendsService.getFriendsList((int) userId);

        // Assert
        assertEquals("[]", result);
        verify(userRepository, times(1)).findById((long) userId);
        verify(friendsRepository, times(1)).findAll();
    }

}

