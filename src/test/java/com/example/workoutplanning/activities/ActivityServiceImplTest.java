package com.example.workoutplanning.activities;

import com.example.workoutplanning.achievements.repositories.AchievementDataRepository;
import com.example.workoutplanning.activities.model.Activity;
import com.example.workoutplanning.activities.repositories.ActivityRepository;
import com.example.workoutplanning.activities.services.ActivityServiceImpl;
import com.example.workoutplanning.goals.repositories.GoalRepository;
import com.example.workoutplanning.users.model.User;
import com.example.workoutplanning.users.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ActivityServiceImplTest {

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GoalRepository goalRepository;

    @Mock
    private AchievementDataRepository achievementDataRepository;

    @InjectMocks
    private ActivityServiceImpl activityService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateActivity() {
        //Mocking
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(activityRepository.save(any(Activity.class))).thenReturn(new Activity());
        when(goalRepository.updateGoalProgress(anyInt(), anyInt())).thenReturn(1);
        when(goalRepository.updateGoalCompletion(anyInt(),anyInt())).thenReturn(1);
        when(achievementDataRepository.updateAchievementsData(anyInt(),anyInt())).thenReturn(1);


        //Execution
        String result = activityService.createActivity(1, 1, 1);

        //Verification
        assertEquals("Activity created! \n Goals updated: 1"
                + "! \n Goals completed: 1"
                + "! \n Achievements updated: 1"
                + "!", result);
        verify(userRepository, times(1)).findById(anyLong());
        verify(activityRepository, times(1)).save(any(Activity.class));
    }

    @Test
    public void testCreateActivity_UserNotFound() {
        //Mocking
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(activityRepository.save(any(Activity.class))).thenReturn(new Activity());
        when(goalRepository.updateGoalProgress(anyInt(), anyInt())).thenReturn(1);
        when(goalRepository.updateGoalCompletion(anyInt(),anyInt())).thenReturn(1);
        when(achievementDataRepository.updateAchievementsData(anyInt(),anyInt())).thenReturn(1);

        //Execution and Verification
        assertThrows(RuntimeException.class, () -> activityService.createActivity(1, 1, 1));

        verify(userRepository, times(1)).findById(anyLong());
        verify(activityRepository, never()).save(any(Activity.class));
    }


    @Test
    public void testGetActivity() {
        //Mocking
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(activityRepository.findById(anyLong())).thenReturn(Optional.of(new Activity()));

        //Execution
        String result = activityService.getActivity(1, 1);

        //Verification
        assertEquals(new Activity().toString(), result);
        verify(userRepository, times(1)).findById(anyLong());
        verify(activityRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testGetActivity_UserNotFound() {
        //Mocking
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(activityRepository.findById(anyLong())).thenReturn(Optional.of(new Activity()));

        //Execution and Verification
        assertThrows(RuntimeException.class, () -> activityService.getActivity(1, 1));

        verify(userRepository, times(1)).findById(anyLong());
        verify(activityRepository, never()).findById(anyLong());
    }

    @Test
    public void testGetActivity_ActivityNotFound() {
        //Mocking
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(activityRepository.findById(anyLong())).thenReturn(Optional.empty());

        //Execution and Verification
        assertThrows(RuntimeException.class, () -> activityService.getActivity(1, 1));

        verify(userRepository, times(1)).findById(anyLong());
        verify(activityRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testGetAllActivities() {
        //Mocking
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(activityRepository.findAll()).thenReturn(new ArrayList<>(List.of(new Activity(), new Activity())));

        //Execution
        String result = activityService.getAllActivities(1);

        //Verification
        verify(activityRepository, times(1)).findAll();
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testGetAllActivities_UserNotFound() {
        //Mocking
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(activityRepository.findAll()).thenReturn(java.util.List.of(new Activity(), new Activity()));

        //Execution and Verification
        assertThrows(RuntimeException.class, () -> activityService.getAllActivities(1));

        verify(activityRepository, never()).findAll();
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testUpdateActivity() {
        //Mocking
        int userId = 1;
        int activityId = 1;
        Activity activity = new Activity();
        activity.setId(activityId);
        activity.setUser_id(userId);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(activityRepository.findById(anyLong())).thenReturn(Optional.of(activity));
        when(goalRepository.updateGoalProgress(anyInt(), anyInt())).thenReturn(1);
        when(goalRepository.updateGoalCompletion(anyInt(),anyInt())).thenReturn(1);
        when(achievementDataRepository.updateAchievementsData(anyInt(),anyInt())).thenReturn(1);


        //Execution
        String result = activityService.updateActivity(1, 1, 1, 1);

        //Verification
        assertEquals("Activity updated! \n Goals updated: 1"
                + "! \n Goals completed: 1"
                + "! \n Achievements updated: 1"
                + "!", result);
        verify(userRepository, times(1)).findById(anyLong());
        verify(activityRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testUpdateActivity_UserNotFound() {
        //Mocking
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(activityRepository.findById(anyLong())).thenReturn(Optional.of(new Activity()));
        when(goalRepository.updateGoalProgress(anyInt(), anyInt())).thenReturn(1);
        when(goalRepository.updateGoalCompletion(anyInt(),anyInt())).thenReturn(1);
        when(achievementDataRepository.updateAchievementsData(anyInt(),anyInt())).thenReturn(1);

        //Execution and Verification
        assertThrows(RuntimeException.class, () -> activityService.updateActivity(1, 1, 1, 1));

        verify(userRepository, times(1)).findById(anyLong());
        verify(activityRepository, never()).findById(anyLong());
    }

    @Test
    public void testUpdateActivity_ActivityNotFound() {
        //Mocking
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(activityRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(goalRepository.updateGoalProgress(anyInt(), anyInt())).thenReturn(1);
        when(goalRepository.updateGoalCompletion(anyInt(),anyInt())).thenReturn(1);
        when(achievementDataRepository.updateAchievementsData(anyInt(),anyInt())).thenReturn(1);

        //Execution and Verification
        assertThrows(RuntimeException.class, () -> activityService.updateActivity(1, 1, 1, 1));

        verify(userRepository, times(1)).findById(anyLong());
        verify(activityRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testDeleteActivity() {
        //Mocking
        int userId = 1;
        int activityId = 1;
        Activity activity = new Activity();
        activity.setId(activityId);
        activity.setUser_id(userId);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(activityRepository.findById(anyLong())).thenReturn(Optional.of(activity));
        when(goalRepository.updateGoalProgress(anyInt(), anyInt())).thenReturn(1);
        when(goalRepository.updateGoalCompletion(anyInt(),anyInt())).thenReturn(1);
        when(achievementDataRepository.updateAchievementsData(anyInt(),anyInt())).thenReturn(1);

        //Execution
        String result = activityService.deleteActivity(1, 1);

        //Verification
        assertEquals("Activity deleted! \n Goals updated: 1"
                + "! \n Goals completed: 1"
                + "! \n Achievements updated: 1"
                + "!", result);
        verify(userRepository, times(1)).findById(anyLong());
        verify(activityRepository, times(1)).findById(anyLong());
        verify(activityRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void testDeleteActivity_UserNotFound() {
        //Mocking
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(activityRepository.findById(anyLong())).thenReturn(Optional.of(new Activity()));
        when(goalRepository.updateGoalProgress(anyInt(), anyInt())).thenReturn(1);
        when(goalRepository.updateGoalCompletion(anyInt(),anyInt())).thenReturn(1);
        when(achievementDataRepository.updateAchievementsData(anyInt(),anyInt())).thenReturn(1);

        //Execution and Verification
        assertThrows(RuntimeException.class, () -> activityService.deleteActivity(1, 1));

        verify(userRepository, times(1)).findById(anyLong());
        verify(activityRepository, never()).findById(anyLong());
        verify(activityRepository, never()).deleteById(anyLong());
    }

    @Test
    public void testDeleteActivity_ActivityNotFound() {
        //Mocking
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(activityRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(goalRepository.updateGoalProgress(anyInt(), anyInt())).thenReturn(1);
        when(goalRepository.updateGoalCompletion(anyInt(),anyInt())).thenReturn(1);
        when(achievementDataRepository.updateAchievementsData(anyInt(),anyInt())).thenReturn(1);

        //Execution and Verification
        assertThrows(RuntimeException.class, () -> activityService.deleteActivity(1, 1));

        verify(userRepository, times(1)).findById(anyLong());
        verify(activityRepository, times(1)).findById(anyLong());
        verify(activityRepository, never()).deleteById(anyLong());
    }
}

