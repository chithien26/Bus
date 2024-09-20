package com.lct.bus.service;

import com.lct.bus.dto.RouteDTO;
import com.lct.bus.dto.UserDTO;
import com.lct.bus.models.Route;
import com.lct.bus.models.User;
import com.lct.bus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
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
        return userRepository.findByUsername(username);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.getByUsername(username);
        User user = userRepository.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()
        );
    }



    public void updateUser(UserDTO userDTO) {
        User userUpdate = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new RuntimeException("Route not found"));

        userUpdate.setUsername(userDTO.getUsername());
        userUpdate.setPassword(userDTO.getPassword());
        userUpdate.setFirstName(userDTO.getFirstName());
        userUpdate.setLastName(userDTO.getLastName());
        userUpdate.setPhone(userDTO.getPhone());
        userUpdate.setEmail(userDTO.getEmail());
        userUpdate.setRole(userDTO.getRole());
        userUpdate.setAvatar(userDTO.getAvatar());
        userUpdate.setActive(userDTO.getActive());
        userRepository.save(userUpdate);
    }
    public void createUser(User user) {
        Boolean existsRoute = userRepository.existsById(user.getId());
        if(existsRoute){
            new RuntimeException("User đã tồn tại");
        }
        else {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            User u = new User();
            u.setUsername(user.getUsername());
            u.setPassword(encodedPassword);
            u.setFirstName(user.getFirstName());
            u.setLastName(user.getLastName());
            u.setPhone(user.getPhone());
            u.setEmail(user.getEmail());
            if (user.getRole() == null || user.getRole().isEmpty())
                u.setRole("USER");
            else
                u.setRole(user.getRole());
            u.setAvatar(user.getAvatar());
            u.setCreatedDate(LocalDateTime.now());
            u.setActive(true);
            userRepository.save(u);

        }
    }
}
