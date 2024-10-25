package com.lct.bus.controllers.userControllers;

import com.lct.bus.dto.FavouriteDTO;
import com.lct.bus.models.Favourite;
import com.lct.bus.models.User;
import com.lct.bus.repository.UserRepository;
import com.lct.bus.service.FavouriteService;
import com.lct.bus.service.RouteService;
import com.lct.bus.service.StationService;
import com.lct.bus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("favourite")
public class FavouriteController {
    @Autowired
    private FavouriteService favouriteService;

    @Autowired
    private UserService userService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private StationService stationService;

    @GetMapping
    public ResponseEntity<List<Favourite>> getFavoriteRoutes() {
        // Lấy thông tin user hiện tại từ SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getClass().getName();

        // Tìm user dựa trên username
        User user = userService.getUserByUsername(username);

        // Lấy danh sách tuyến đường yêu thích
        List<Favourite> favoriteRoutes = favouriteService.getFavouriteByUserId(user.getId());

        return ResponseEntity.ok(favoriteRoutes);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addFavourite(@RequestParam(value = "routeId") int routeId,
                                               @RequestParam(value = "startStationId") int startStationId,
                                               @RequestParam(value = "endStationId") int endStationId) {
        try {
            // Lấy thông tin người dùng hiện tại
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = authentication.getName();
            User currentUser = userService.getUserByUsername(currentUsername);

            // Tạo đối tượng FavouriteDTO và gán giá trị
            FavouriteDTO favourite = new FavouriteDTO();
            favourite.setUser(currentUser); // Không cần ép kiểu (User)
            favourite.setRoute(routeService.getRouteById(routeId));
            favourite.setStartStation(stationService.getStationById(startStationId)); // Sửa lỗi chính tả
            favourite.setEndStation(stationService.getStationById(endStationId));

            // Lưu đối tượng yêu thích
            favouriteService.createFavourite(favourite);

            // Trả về phản hồi thành công
            return ResponseEntity.ok("Favourite added successfully");
        } catch (Exception e) {
            // Xử lý ngoại lệ, trả về HTTP Status 500 và thông điệp lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding favourite: " + e.getMessage());
        }
    }

}
