package org.kashcode.todoapp.services;

import org.kashcode.todoapp.data.models.User;
import org.kashcode.todoapp.data.repositories.UserRepository;
import org.kashcode.todoapp.dtos.requests.LoginRequest;
import org.kashcode.todoapp.dtos.requests.UserRequest;
import org.kashcode.todoapp.dtos.requests.UserUpdateRequest;
import org.kashcode.todoapp.dtos.responses.UserResponse;
import org.kashcode.todoapp.exceptions.DuplicateUserException;
import org.kashcode.todoapp.exceptions.InvalidCredentialsException;
import org.kashcode.todoapp.exceptions.UserNotFoundException;
import org.kashcode.todoapp.utils.UserMapper;
import org.kashcode.todoapp.utils.PasswordUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public UserResponse register(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateUserException("Username already taken: " + request.getUsername());
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateUserException("Email already taken: " + request.getEmail());
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(PasswordUtils.encodePassword(request.getPassword()));

        User saved = userRepository.save(user);

        String token = jwtService.generateToken(saved.getUsername());
        return UserMapper.toResponse(saved, token);
    }

    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        if (request.getUsername() != null) user.setUsername(request.getUsername());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPassword() != null) user.setPassword(PasswordUtils.encodePassword(request.getPassword()));

        User updated = userRepository.save(user);

        String token = jwtService.generateToken(updated.getUsername());
        return UserMapper.toResponse(updated, token);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> UserMapper.toResponse(user, null))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return UserMapper.toResponse(user, null);
    }

    @Override
    public UserResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsernameOrEmail())
                .or(() -> userRepository.findByEmail(request.getUsernameOrEmail()))
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!PasswordUtils.verify(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        String token = jwtService.generateToken(user.getUsername());
        return UserMapper.toResponse(user, token);
    }

}
