package org.example.musicsheets.services;

import org.example.musicsheets.exceptions.UserAlreadyExistsException;
import org.example.musicsheets.exceptions.UserNotFoundException;
import org.example.musicsheets.models.User;
import org.example.musicsheets.models.UserRole;
import org.example.musicsheets.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        if (userRepository.existsByLogin(user.getLogin())) {
            throw new UserAlreadyExistsException("User with this login already exists.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.USER);
        return userRepository.save(user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID: " + userId + " not found"));
    }

    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("User with login: " + login + " not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Page<User> getAllUsersPaginated(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User updateWholeUser(Long userId, User updatedUser) {
        User existingUser = getUserById(userId);

        updateUserWhole(existingUser, updatedUser);

        return userRepository.save(existingUser);
    }

    private void updateUserWhole(User existingUser, User updatedUser) {
        existingUser.setLogin(updatedUser.getLogin());
        existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setAvatarUrl(updatedUser.getAvatarUrl());
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User with ID: " + userId + " not found");
        }

        userRepository.deleteById(userId);
    }
}
