package com.nqh.jwt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.nqh.jwt.entity.UserInfo;
import com.nqh.jwt.service.UserInfoService;

import jakarta.servlet.http.HttpServletRequest;


/*
 * HomeController là class chứa các phương thức xử lý liên quan đến trang chủ
 * 
 * welcome: phương thức trả về thông báo chào mừng
 * getAllUsers: phương thức trả về danh sách tất cả user
 * getUser: phương thức trả về thông tin user theo id
 * 
 * Class này sẽ được sử dụng trong JwtApplication
 */
@RestController
public class HomeController {

    @Autowired
    private UserInfoService userInfoService;

     @GetMapping("/welcome")
    public String welcome(HttpServletRequest request) {
        return "Welcome to Spring Security tutorials !!";
    }

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserInfo> getAllUsers() {
        return userInfoService.getAllUser();
    }

    @GetMapping("/getUser/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public UserInfo getAllUsers(@PathVariable Integer id) {
        return userInfoService.getUser(id);
    }

}
