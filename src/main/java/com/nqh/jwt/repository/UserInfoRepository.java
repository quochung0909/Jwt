package com.nqh.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nqh.jwt.entity.UserInfo;

import java.util.Optional;

/*
 * UserInfoRepository là interface kế thừa từ JpaRepository
 * 
 * JpaRepository là một interface trong Spring Data JPA, cung cấp các phương thức CRUD cho một Entity
 * 
 * UserInfoRepository sẽ được sử dụng để thao tác với UserInfo trong database
 * 
 * @Repository: Đánh dấu đây là một Repository, là nơi chứa các phương thức thao tác với database
 * 
 * Class này sẽ được sử dụng trong JwtApplication
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> {
    Optional<UserInfo> findByName(String userName);
}
