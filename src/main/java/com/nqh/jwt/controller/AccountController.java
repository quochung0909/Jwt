package com.nqh.jwt.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.nqh.jwt.entity.AuthRequest;
import com.nqh.jwt.entity.Response;
import com.nqh.jwt.entity.Token;
import com.nqh.jwt.entity.UserInfo;
import com.nqh.jwt.service.JwtService;
import com.nqh.jwt.service.TokenService;
import com.nqh.jwt.service.UserInfoDetails;
import com.nqh.jwt.service.UserInfoService;


/*
 * AccountController là class chứa các phương thức xử lý liên quan đến tài khoản
 * 
 * addUser: phương thức thêm mới user
 * login: phương thức đăng nhập
 * 
 * Class này sẽ được sử dụng trong JwtApplication
 * 
 */
@RestController
@RequestMapping("/auth")
public class AccountController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private TokenService tokenService;

    // Phương thức này sẽ thêm mới user
    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody UserInfo userInfo) {
        try {

            // Kiểm tra xem username đã tồn tại chưa 
            if (userInfoService.findByName(userInfo.getName()).isPresent()) {
                // Nếu username đã tồn tại, trả về thông báo lỗi
                Response res = new Response();
                res.setMessage("Username is already taken");
                res.setStatus(HttpStatus.BAD_REQUEST);
                res.setTimestamp(String.valueOf(System.currentTimeMillis()));

                // Trả về response thông báo lỗi
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            } else {
                // Nếu username chưa tồn tại, thêm mới user
                userInfoService.addUser(userInfo);

                // Trả về thông báo thành công
                Response res = new Response();
                res.setMessage("User added successfully");
                res.setStatus(HttpStatus.OK);
                res.setTimestamp(String.valueOf(System.currentTimeMillis()));

                // Trả về response thông báo thành công
                return new ResponseEntity<>(res, HttpStatus.OK);
            }

        } catch (Exception e) {

            // Nếu có lỗi xảy ra, trả về thông báo lỗi
            Response res = new Response();
            res.setMessage("Internal server error");
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            res.setTimestamp(String.valueOf(System.currentTimeMillis()));

            // Trả về response thông báo lỗi
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // Phương thức này sẽ xử lý đăng nhập
    @PostMapping("/login")
    public ResponseEntity<Response> addUser(@RequestBody AuthRequest authRequest) {
        try {

            // Thêm thông tin đăng nhập vào context để spring security kiểm tra 
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authenticate); 

            // Kiểm tra xem token đã tồn tại trong database hay chưa
            UserInfo userInfo = userInfoService.findByName(authRequest.getUsername()).get();
            Token existingToken = tokenService.findByUserInfo(userInfo);

            // Tạo token
            String token = null;

            // Tao đối tượng UserDetails từ UserInfo
            UserInfoDetails userDetails = new UserInfoDetails(userInfo);

            // Nếu token đã tồn tại, kiểm tra xem token còn hạn hay không
            if (existingToken != null) {


                // Kiểm tra token còn hạn hay không 
                if (jwtService.validateToken(existingToken.getToken(), userDetails)) {

                    // Nếu token còn hạn, sử dụng lại token cũ
                    token = existingToken.getToken();
                } else {

                    // Nếu token hết hạn, tạo mới token
                    token = jwtService.generateToken(userDetails.getUsername());

                    // Lấy token cũ và cập nhật lại thông tin
                    existingToken.setToken(token);
                    existingToken.setTimestamp(String.valueOf(System.currentTimeMillis()));
                    existingToken.setExpiration(jwtService.extractExpiration(token));

                    // Lưu token mới vào database
                    tokenService.update(existingToken);
                }
                    
            } else {
                // Nếu token chưa tồn tại, tạo mới token
                token = jwtService.generateToken(userDetails.getUsername());

                // Lưu token vào database
                String timestamp = String.valueOf(System.currentTimeMillis());
                tokenService.save(token, "Login success", timestamp, jwtService.extractExpiration(token), userInfo);
            }

            // thời gian hiện tại 
            String timestamp = String.valueOf(System.currentTimeMillis());

            // Tạo đối tượng Response chứa token và trả về cho người dùng 
            Response res = new Response();
            res.setToken(token);
            res.setMessage("Login success");
            res.setStatus(HttpStatus.OK);
            res.setTimestamp(timestamp);
            res.setExpiration(jwtService.extractExpiration(token));

            // Trả về response
            return new ResponseEntity<>(res, HttpStatus.OK);

        } catch (BadCredentialsException e) {

            // Nếu thông tin đăng nhập không chính xác, trả về thông báo lỗi
            Response res = new Response();
            res.setMessage("Invalid username or password");
            res.setStatus(HttpStatus.UNAUTHORIZED);
            res.setTimestamp(String.valueOf(System.currentTimeMillis()));

            // Trả về response thông báo lỗi
            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {

            // Nếu có lỗi xảy ra, trả về thông báo lỗi
            Response res = new Response();
            res.setMessage("Internal server error");
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            res.setTimestamp(String.valueOf(System.currentTimeMillis()));

            // Trả về response thông báo lỗi
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
