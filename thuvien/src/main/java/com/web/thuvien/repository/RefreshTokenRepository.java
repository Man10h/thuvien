package com.web.thuvien.repository;

import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.web.thuvien.model.entity.RefreshTokenEntity;
import com.web.thuvien.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    void deleteByUserEntity(UserEntity userEntity);
}
