package com.nqh.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/*
 * AuthRequest là class dùng để nhận thông tin đăng nhập từ client
 * 
 * username: tên đăng nhập
 * password: mật khẩu
 * 
 * Class này sẽ được sử dụng trong phương thức login của AccountController
 * 
 * @Data: Lombok annotation, tự sinh ra các hàm getter, setter, constructor, toString, equals, hashCode
 * @AllArgsConstructor: Lombok annotation, tự sinh ra constructor với tất cả tham số
 * @NoArgsConstructor: Lombok annotation, tự sinh ra constructor không có tham số
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    private String username;
    private String password;
}
