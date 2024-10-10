package com.lct.bus.components;

import com.lct.bus.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain chain) throws jakarta.servlet.ServletException, IOException {
        // Lấy header Authorization từ request
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // Kiểm tra xem header có chứa token hay không
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            try {
                // Trích xuất username từ token
                username = jwtUtil.extractUsername(token);
            } catch (Exception e) {
                System.out.println("JWT Token không hợp lệ: " + e.getMessage());
            }
        }

        // Kiểm tra nếu username tồn tại và SecurityContext chưa có xác thực
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Tải thông tin người dùng từ database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Kiểm tra tính hợp lệ của token
            if (jwtUtil.validateToken(token, userDetails)) {
                // Tạo đối tượng xác thực cho người dùng
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Đặt xác thực vào SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // Tiếp tục các bộ lọc tiếp theo
        chain.doFilter(request, response);
    }
}
