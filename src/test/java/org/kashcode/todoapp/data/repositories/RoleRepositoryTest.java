package org.kashcode.todoapp.data.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kashcode.todoapp.data.models.Role;
import org.kashcode.todoapp.data.models.Todo;
import org.kashcode.todoapp.data.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")

class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void cleanDatabase() {
        roleRepository.deleteAll();
    }

    @Test
    void testSaveRole() {
        Role role = new Role();
        role.setName("ROLE_USER");

        Role saved = roleRepository.save(role);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("ROLE_USER");
    }


    @Test
    void testFindAllRoles() {
        Role role1 = new Role();
        role1.setName("ROLE_USER");

        Role role2 = new Role();
        role2.setName("ROLE_ADMIN");

        roleRepository.save(role1);
        roleRepository.save(role2);

        List<Role> roles = roleRepository.findAll();

        assertThat(roles).hasSize(2);
        assertThat(roles).extracting("name").containsExactlyInAnyOrder("ROLE_USER", "ROLE_ADMIN");
    }

    @Test
    void testFindByName() {
        Role role = new Role();
        role.setName("ROLE_USER");
        roleRepository.save(role);

        Optional<Role> found = roleRepository.findByName("ROLE_USER");

        assertThat(found).isNotNull();
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getName()).isEqualTo("ROLE_USER");
    }

    @Test
    void testDeleteRole() {
        Role role = new Role();
        role.setName("ROLE_USER");

        Role saved = roleRepository.save(role);

        roleRepository.delete(saved);

        assertThat(roleRepository.findAll()).isEmpty();
    }

}