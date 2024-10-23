package com.lct.bus.controllers.userControllers;

import com.lct.bus.models.User;
import com.lct.bus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String hello() {
        return "hello";
    }

    @GetMapping("/current-user")
    public ResponseEntity<User> userDetails() {
        // Lấy thông tin authentication hiện tại từ SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra xem authentication có phải là UserDetails không
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            // Tìm user trong cơ sở dữ liệu dựa trên username
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            // Trả về thông tin user
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.badRequest().build();
    }
}
