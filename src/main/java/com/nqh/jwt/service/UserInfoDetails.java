package com.nqh.jwt.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nqh.jwt.entity.UserInfo;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/*
 * UserInfoDetails là class implement UserDetails
 * 
 * UserDetails là một interface trong Spring Security, cung cấp các phương thức để lấy thông tin về người dùng
 * 
 * Class này sẽ được sử dụng trong JwtUserDetailsService
 */
public class UserInfoDetails implements UserDetails {
    String userName=null;
    String password = null;
    List<GrantedAuthority> authorities;

    public UserInfoDetails(UserInfo userInfo){
       userName= userInfo.getName();
       password= userInfo.getPassword();
       authorities= Arrays.stream(userInfo.getRoles().split(","))
               .map(SimpleGrantedAuthority::new)
               .collect(Collectors.toList());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
