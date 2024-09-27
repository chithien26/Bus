package com.lct.bus.service;

import com.lct.bus.dto.RouteDTO;
import com.lct.bus.dto.UserDTO;
import com.lct.bus.models.Route;
import com.lct.bus.models.User;
import com.lct.bus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUserByUsername(String username) {
        return userRepository.getAllUserByUsername(username);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }


    public void updateUser(User user) {
        User userUpdate = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("Route not found"));
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        userUpdate.setUsername(user.getUsername());
        userUpdate.setPassword(encodedPassword);
        userUpdate.setFirstName(user.getFirstName());
        userUpdate.setLastName(user.getLastName());
        userUpdate.setPhone(user.getPhone());
        userUpdate.setEmail(user.getEmail());
        userUpdate.setRole(user.getRole());
        userUpdate.setAvatar(user.getAvatar());
        userUpdate.setActive(user.getActive());
        userRepository.save(userUpdate);
    }
    public void createUser(UserDTO userDTO) {
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        User u = new User();
        u.setUsername(userDTO.getUsername());
        u.setPassword(encodedPassword);
        u.setFirstName(userDTO.getFirstName());
        u.setLastName(userDTO.getLastName());
        u.setPhone(userDTO.getPhone());
        u.setEmail(userDTO.getEmail());
        if (userDTO.getRole() == null || userDTO.getRole().isEmpty())
            u.setRole("ROLE_USER");
        else
            u.setRole(userDTO.getRole());
        u.setAvatar(userDTO.getAvatar());
        u.setCreatedDate(LocalDateTime.now());
        u.setActive(true);
        userRepository.save(u);

    }
}
