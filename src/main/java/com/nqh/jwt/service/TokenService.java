package com.nqh.jwt.service;

import java.util.Date;

import com.nqh.jwt.entity.Token;
import com.nqh.jwt.entity.UserInfo;

/*
 * TokenService là interface chứa các phương thức thao tác với Token
 * 
 * Class này sẽ được sử dụng trong JwtApplication
 */
public interface TokenService {
    void save(String token, String message, String timestamp, Date expiration, UserInfo userInfo);

    void update(Token token);

    Token findByUserInfo(UserInfo userInfo);
}
