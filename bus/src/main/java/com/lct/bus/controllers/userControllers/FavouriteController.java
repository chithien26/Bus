package com.lct.bus.controllers.userControllers;

import com.lct.bus.models.Favourite;
import com.lct.bus.models.User;
import com.lct.bus.repository.UserRepository;
import com.lct.bus.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("favourite")
public class FavouriteController {
    @Autowired
    private FavouriteService favouriteService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Favourite>> getFavoriteRoutes() {
        // Lấy thông tin user hiện tại từ SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getClass().getName();

        // Tìm user dựa trên username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Lấy danh sách tuyến đường yêu thích
        List<Favourite> favoriteRoutes = favouriteService.getFavouriteByUserId(user.getId());

        return ResponseEntity.ok(favoriteRoutes);
    }
}
