package com.nqh.jwt.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Token là class dùng để lưu thông tin token
 * 
 * id: id của token
 * token: token được sinh ra từ JWT
 * message: thông báo
 * timestamp: thời gian tạo token
 * expiration: thời gian hết hạn của token
 * userInfo: thông tin user
 * 
 * Class này sẽ được sử dụng trong JwtApplication
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String token;
    private String message;
    private String timestamp;
    private Date expiration;
    @ManyToOne(targetEntity = UserInfo.class)
    private UserInfo userInfo;
}
