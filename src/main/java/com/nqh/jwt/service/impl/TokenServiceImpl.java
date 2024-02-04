package com.nqh.jwt.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nqh.jwt.entity.Token;
import com.nqh.jwt.entity.UserInfo;
import com.nqh.jwt.repository.TokenRepository;
import com.nqh.jwt.service.TokenService;


/*
 * TokenServiceImpl là class thực thi interface TokenService
 * 
 * Class này sẽ được sử dụng trong JwtApplication
 */
@Service
public class TokenServiceImpl implements TokenService{

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public void save(String token, String message, String timestamp, Date expiration, UserInfo userInfo) {
        Token tokenEntity = new Token();
        tokenEntity.setToken(token);
        tokenEntity.setMessage(message);
        tokenEntity.setTimestamp(timestamp);
        tokenEntity.setExpiration(expiration);
        tokenEntity.setUserInfo(userInfo);
        tokenRepository.save(tokenEntity);
    }

    @Override
    public Token findByUserInfo(UserInfo userInfo) {
        return tokenRepository.findByUserInfo(userInfo);
    }
    
    @Override
    public void update(Token token) {
        tokenRepository.save(token);
    }
}
