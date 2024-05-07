package com.nolan.musicsheetsportal.sevices;

import com.nolan.musicsheetsportal.config.MyUserDetails;
import com.nolan.musicsheetsportal.models.MyUser;
import com.nolan.musicsheetsportal.repositories.CommentRepository;
import com.nolan.musicsheetsportal.repositories.LikeRepository;
import com.nolan.musicsheetsportal.repositories.SheetRepository;
import com.nolan.musicsheetsportal.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SheetRepository sheetRepository;

    public MyUserDetailService() {}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> user = userRepository.findByUsername(username);

        return user.map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }

    public void addUser(MyUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


    @Transactional
    public void deleteUser(long id) {
        Optional<MyUser> user = userRepository.findById(id);

        if (user.isPresent()) {
            likeRepository.deleteAllByMyUser(user.get());
            commentRepository.deleteAllByAuthor(user.get());
            sheetRepository.deleteByAuthorId(id);
            userRepository.deleteById(id);

        } else {
            throw new UsernameNotFoundException("User with id " + id + " not found");
        }
    }

    public boolean hasId(long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<MyUser> user = userRepository.findByUsername(username);

        return user.filter(myUser -> myUser.getId() == id).isPresent();
    }

    public List<MyUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void updateUser(long id, MyUser updatedUser) {
        if (id != updatedUser.getId())
            throw new RuntimeException("Different id: " + id);

        MyUser existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User with id " + id + " not found"));

        existingUser.setAvatarUrl(updatedUser.getAvatarUrl());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setPassword(updatedUser.getPassword());

        userRepository.save(existingUser);
    }
}
