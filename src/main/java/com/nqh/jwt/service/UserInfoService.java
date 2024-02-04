package com.nqh.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.nqh.jwt.entity.UserInfo;
import com.nqh.jwt.repository.UserInfoRepository;

import java.util.List;
import java.util.Optional;

/*
 * UserInfoService là class implement UserDetailsService
 * 
 * UserDetailsService là một interface trong Spring Security, cung cấp các phương thức để lấy thông tin về người dùng
 * 
 * Class này sẽ được sử dụng trong JwtUserDetailsService
 */
@Service
public class UserInfoService implements UserDetailsService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userInfoRepository.findByName(username);
        return userInfo.map(UserInfoDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"+username));
    }
    public String addUser(UserInfo userInfo){
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
        return "User added successfully";
    }
    public List<UserInfo> getAllUser(){
         return userInfoRepository.findAll();
    }
    public UserInfo getUser(Integer id){
        return userInfoRepository.findById(id).get();
    }

    public Optional<UserInfo> findByName(String name){
        return userInfoRepository.findByName(name);
    }
}
