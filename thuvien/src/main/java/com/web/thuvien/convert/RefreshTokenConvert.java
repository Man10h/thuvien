package com.web.thuvien.convert;


import com.web.thuvien.model.entity.RefreshTokenEntity;
import com.web.thuvien.model.response.RefreshTokenResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenConvert {
    @Autowired
    private ModelMapper modelMapper;

    public RefreshTokenResponse convertToRefreshTokenResponse(RefreshTokenEntity refreshTokenEntity) {
        return modelMapper.map(refreshTokenEntity, RefreshTokenResponse.class);
    }
}
