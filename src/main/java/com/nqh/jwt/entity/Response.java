package com.nqh.jwt.entity;

import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/*
 * Response là class dùng để trả về thông báo cho client
 * 
 * token: token được sinh ra từ JWT
 * message: thông báo
 * status: trạng thái của response
 * timestamp: thời gian tạo response
 * expiration: thời gian hết hạn của token
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
public class Response {
    private String token;
    private String message;
    private HttpStatus status;
    private String timestamp;
    private Date expiration;
}
