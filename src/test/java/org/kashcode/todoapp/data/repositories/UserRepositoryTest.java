package org.kashcode.todoapp.data.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kashcode.todoapp.data.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")

class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void cleanDatabase() {
        userRepository.deleteAll();
    }

    @Test
    void testSaveUser() {
        User user = new User();
        user.setUsername("olamide");
        user.setEmail("olamide@example.com");
        user.setPassword("secret");

        User saved = userRepository.save(user);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getUsername()).isEqualTo("olamide");
    }

    @Test
    void testFindAllUsers() {
        User user1 = new User();
        user1.setUsername("olamide");
        user1.setEmail("olamide@example.com");
        user1.setPassword("secret");

        User user2 = new User();
        user2.setUsername("divine");
        user2.setEmail("divin@example.com");
        user2.setPassword("password");

        userRepository.save(user1);
        userRepository.save(user2);

        List<User> users = userRepository.findAll();

        assertThat(users).hasSize(2);
        assertThat(users).extracting(User::getUsername)
                .containsExactlyInAnyOrder("olamide", "divine");
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setUsername("olamide");
        user.setEmail("olamide@example.com");
        user.setPassword("secret");

        userRepository.save(user);
        userRepository.delete(user);

        assertThat(userRepository.findAll()).isEmpty();
    }
}
