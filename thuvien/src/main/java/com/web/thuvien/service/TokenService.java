package com.web.thuvien.service;

import com.web.thuvien.model.entity.UserEntity;

public interface TokenService {
    public String generateToken(UserEntity userEntity);
    public String generateRefreshToken(UserEntity userEntity);
    public boolean validateToken(String token);
    public String getUsername(String token);
}
