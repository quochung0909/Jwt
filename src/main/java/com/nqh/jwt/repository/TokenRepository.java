package com.nqh.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nqh.jwt.entity.Token;
import com.nqh.jwt.entity.UserInfo;

/*
 * TokenRepository là interface kế thừa từ JpaRepository
 * 
 * JpaRepository là một interface trong Spring Data JPA, cung cấp các phương thức CRUD cho một Entity
 * 
 * TokenRepository sẽ được sử dụng để thao tác với Token trong database
 * 
 * @Repository: Đánh dấu đây là một Repository, là nơi chứa các phương thức thao tác với database
 * 
 * Class này sẽ được sử dụng trong JwtApplication
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, Integer>{
    Token findByUserInfo(UserInfo userInfo);
}
