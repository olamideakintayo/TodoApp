package org.kashcode.todoapp.services;

import org.kashcode.todoapp.data.models.Role;
import org.kashcode.todoapp.data.models.User;
import org.kashcode.todoapp.data.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    private UserRepository userRepository;
    private CustomUserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userDetailsService = new CustomUserDetailsService(userRepository);
    }

    @Test
    void loadUserByUsername_ShouldReturnUserDetails_WhenUserFoundByUsername() {
        User user = new User();
        user.setUsername("john");
        user.setPassword("secret");
        user.setEmail("john@example.com");

        Role role = new Role();
        role.setName("USER");
        user.setRoles(List.of(role));

        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));


        UserDetails result = userDetailsService.loadUserByUsername("john");


        assertEquals("john", result.getUsername());
        assertEquals("secret", result.getPassword());
        assertTrue(result.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
        verify(userRepository, times(1)).findByUsername("john");
    }

    @Test
    void loadUserByUsername_ShouldReturnUserDetails_WhenUserFoundByEmail() {
        User user = new User();
        user.setUsername("jane");
        user.setPassword("pass123");
        user.setEmail("jane@example.com");

        Role role = new Role();
        role.setName("ADMIN");
        user.setRoles(List.of(role));

        when(userRepository.findByUsername("jane@example.com")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("jane@example.com")).thenReturn(Optional.of(user));

        UserDetails result = userDetailsService.loadUserByUsername("jane@example.com");

        assertEquals("jane", result.getUsername());
        assertEquals("pass123", result.getPassword());
        assertTrue(result.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        verify(userRepository, times(1)).findByUsername("jane@example.com");
        verify(userRepository, times(1)).findByEmail("jane@example.com");
    }
}
