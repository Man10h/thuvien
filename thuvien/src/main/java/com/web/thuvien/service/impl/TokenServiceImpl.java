package com.web.thuvien.service.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.web.thuvien.model.entity.UserEntity;
import com.web.thuvien.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private SecretKey secretKey;

    @Override
    public String generateToken(UserEntity userEntity) {
        String username = userEntity.getUsername();
        String role = userEntity.getRoleEntity().getRoleName();
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .claim("roles", Collections.singletonList(role))
                .expirationTime(new Date(new Date().getTime() + 1000 * 60 * 60))
                .build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), jwtClaimsSet);
        try{
            JWSSigner jwsSigner = new MACSigner(secretKey);
            signedJWT.sign(jwsSigner);
            return signedJWT.serialize();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public String generateRefreshToken(UserEntity userEntity) {
        String username = userEntity.getUsername();
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .expirationTime(new Date(new Date().getTime() + 1000 * 60 * 60 * 24))
                .build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), jwtClaimsSet);
        try{
            JWSSigner jwsSigner = new MACSigner(secretKey);
            signedJWT.sign(jwsSigner);
            return signedJWT.serialize();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public boolean validateToken(String token) {
        try {
            JWSVerifier jwsVerifier = new MACVerifier(secretKey);
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.verify(jwsVerifier) && new Date(new Date().getTime()).before(signedJWT.getJWTClaimsSet().getExpirationTime()) ;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getUsername(String token) {
        try{
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
            return jwtClaimsSet.getSubject();
        }catch (Exception e){
            return null;
        }
    }
}
