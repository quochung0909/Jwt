package com.nqh.jwt.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/*
 * UserInfo là class định nghĩa cấu trúc của bảng user_info trong database
 * 
 * id: id của user
 * name: tên đăng nhập
 * email: email của user
 * roles: quyền của user
 * password: mật khẩu của user
 * 
 * Class này sẽ được sử dụng trong UserInfoService
 * 
 * @Data: Lombok annotation, tự sinh ra các hàm getter, setter, constructor, toString, equals, hashCode
 * @AllArgsConstructor: Lombok annotation, tự sinh ra constructor với tất cả tham số
 * @NoArgsConstructor: Lombok annotation, tự sinh ra constructor không có tham số
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String name;
    private String email;
    private String roles;
    @NotBlank
    private String password;
    @OneToMany(targetEntity = Token.class, mappedBy = "userInfo")
    private List<Token> tokens;
}
