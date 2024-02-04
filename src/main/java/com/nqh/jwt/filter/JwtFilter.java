package com.nqh.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nqh.jwt.service.JwtService;
import com.nqh.jwt.service.UserInfoService;

import java.io.IOException;

/* 
 * JwtFilter là class lọc request, kiểm tra tính hợp lệ của token
 * 
 * Class này sẽ được sử dụng trong JwtApplication
 */
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserInfoService userInfoService;

    // Phương thức này sẽ kiểm tra tính hợp lệ của token
    @Override
    protected void doFilterInternal(@SuppressWarnings("null") HttpServletRequest request, @SuppressWarnings("null") HttpServletResponse response, @SuppressWarnings("null") FilterChain filterChain) throws ServletException, IOException {
        // Lấy thông tin token từ request
        String authHeader = request.getHeader("Authorization");
        String token= null;
        String userName = null;

        // Kiểm tra xem token có bắt đầu bằng "Bearer" không
        if(authHeader !=null && authHeader.startsWith("Bearer")){
            // Lấy token từ header
            token = authHeader.substring(7);

            // Lấy thông tin username từ token
            userName =jwtService.extractUserName(token);
        }

        // Kiểm tra xem username có hợp lệ không
        if(userName !=null && SecurityContextHolder.getContext().getAuthentication()==null){

            // Lấy thông tin user từ username
            UserDetails userDetails = userInfoService.loadUserByUsername(userName);

            // Kiểm tra tính hợp lệ của token
            if(jwtService.validateToken(token,userDetails)){

                // Nếu token hợp lệ, set thông tin cho SecurityContext
                UsernamePasswordAuthenticationToken authToken =  new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Chuyển request tới filter tiếp theo
        filterChain.doFilter(request,response);
    }
}
