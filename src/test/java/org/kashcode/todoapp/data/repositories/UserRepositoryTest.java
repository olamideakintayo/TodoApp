package org.kashcode.todoapp.data.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kashcode.todoapp.data.models.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser() {
        User user = new User();
        user.setUsername("olamide");
        user.setEmail("olamide@example.com");
        user.setPassword("secret");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername(user.getUsername());
        savedUser.setEmail(user.getEmail());
        savedUser.setPassword(user.getPassword());

        when(userRepository.save(user)).thenReturn(savedUser);

        User result = userRepository.save(user);

        assertThat(result.getId()).isNotNull();
        assertThat(result.getUsername()).isEqualTo("olamide");

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testFindAllUsers() {
        User user1 = new User();
        user1.setUsername("olamide");

        User user2 = new User();
        user2.setUsername("divin");

        List<User> mockUsers = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(mockUsers);

        List<User> users = userRepository.findAll();

        assertThat(users).hasSize(2);
        assertThat(users).extracting(User::getUsername)
                .containsExactlyInAnyOrder("olamide", "divin");

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("olamide");

        doNothing().when(userRepository).delete(user);

        userRepository.delete(user);

        verify(userRepository, times(1)).delete(user);
    }
}
