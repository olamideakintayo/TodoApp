package org.kashcode.todoapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kashcode.todoapp.data.models.User;
import org.kashcode.todoapp.data.repositories.UserRepository;
import org.kashcode.todoapp.dtos.requests.LoginRequest;
import org.kashcode.todoapp.dtos.requests.UserRequest;
import org.kashcode.todoapp.dtos.requests.UserUpdateRequest;
import org.kashcode.todoapp.dtos.responses.UserResponse;
import org.kashcode.todoapp.exceptions.UserNotFoundException;
import org.kashcode.todoapp.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

    @BeforeEach
    void cleanDatabase() {
        userRepository.deleteAll();
        userRepository.flush();
    }

    @Test
    void testThatYouCanRegisterAUser() {
        UserRequest user = new UserRequest();
        user.setUsername("Olamide");
        user.setEmail("olamide@gmail.com");
        user.setPassword("1234567890");

        UserResponse registeredUser = userService.register(user);

        assertNotNull(registeredUser.getToken());
        assertEquals("olamide", registeredUser.getUsername());

        User userFromDb = userRepository.findByUsername(registeredUser.getUsername()).orElseThrow();
        assertEquals(registeredUser.getToken(), jwtService.generateToken(userFromDb.getUsername()));
    }

    @Test
    void testThatYouCanLoginAsAUser() {
        User user = new User();
        user.setUsername("Olamide");
        user.setEmail("olamide@gmail.com");
        user.setPassword(PasswordUtils.encodePassword("1234567890"));
        userRepository.save(user);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail("olamide");
        loginRequest.setPassword("1234567890");

        UserResponse loggedInUser = userService.login(loginRequest);

        assertNotNull(loggedInUser.getToken());
        assertEquals("Olamide", loggedInUser.getUsername());
    }

    @Test
    void testThatYouCanUpdateUserDetails() {
        User user = new User();
        user.setUsername("Olamide");
        user.setEmail("olamide@gmail.com");
        user.setPassword(PasswordUtils.encodePassword("1234567890"));
        userRepository.save(user);
        UserResponse loggedInUser = userService.login(new LoginRequest("olamide", "1234567890"));

        UserUpdateRequest updateUserRequest = new UserUpdateRequest();
        updateUserRequest.setUsername("Olamide2");
        updateUserRequest.setEmail("");
        updateUserRequest.setPassword("");
        UserResponse updatedUser = userService.updateUser(loggedInUser.getId(), updateUserRequest);

        assertEquals("Olamide2", updatedUser.getUsername());
        assertEquals(loggedInUser.getId(), updatedUser.getId());
    }

    @Test
    void testThatYouCanDeleteAUser() {
        User user = new User();
        user.setUsername("Olamide");
        user.setEmail("olamide@gmail.com");
        user.setPassword(PasswordUtils.encodePassword("1234567890"));
        userRepository.save(user);

        Long userId = user.getId();
        userService.deleteUser(userId);

        assertFalse(userRepository.findById(userId).isPresent());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));
    }

    @Test
    void testThatYouCanGetAllUsers() {
        UserRequest user1 = new UserRequest();
        user1.setUsername("Olamide");
        user1.setEmail("olamide@gmail.com");
        user1.setPassword("1234567890");

        UserRequest user2 = new UserRequest();
        user2.setUsername("Segun");
        user2.setEmail("segun@gmail.com");
        user2.setPassword("password123");

        userService.register(user1);
        userService.register(user2);

        var users = userService.getAllUsers();

        assertEquals(2, users.size());
        assertTrue(users.stream().anyMatch(u -> u.getUsername().equals("olamide")));
        assertTrue(users.stream().anyMatch(u -> u.getUsername().equals("segun")));
    }


    @Test
    void testThatYouCanGetUserById() {
        User user = new User();
        user.setUsername("Olamide");
        user.setEmail("olamide@gmail.com");
        user.setPassword(PasswordUtils.encodePassword("1234567890"));
        userRepository.save(user);

        UserResponse foundUser = userService.getUserById(user.getId());

        assertEquals("Olamide", foundUser.getUsername());
        assertEquals("olamide@gmail.com", foundUser.getEmail());
    }

    @Test
    void testThatGetUserByIdThrowsExceptionIfUserNotFound() {
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(999L));
    }
}
