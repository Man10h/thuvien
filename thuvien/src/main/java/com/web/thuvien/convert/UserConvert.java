package com.web.thuvien.convert;

import com.web.thuvien.model.entity.ImageEntity;
import com.web.thuvien.model.entity.RefreshTokenEntity;
import com.web.thuvien.model.entity.UserEntity;
import com.web.thuvien.model.response.ImageResponse;
import com.web.thuvien.model.response.RefreshTokenResponse;
import com.web.thuvien.model.response.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConvert {

    @Autowired
    private ImageConvert imageConvert;

    @Autowired
    private RefreshTokenConvert refreshTokenConvert;

    @Autowired
    private ModelMapper modelMapper;

    public UserResponse convertToUserResponse(UserEntity userEntity) {
        UserResponse userResponse = modelMapper.map(userEntity, UserResponse.class);
        List<ImageEntity> imageEntities = userEntity.getImageEntities();
        List<ImageResponse> imageResponses = new ArrayList<>();
        for(ImageEntity imageEntity : imageEntities) {
            imageResponses.add(imageConvert.convertToImageResponse(imageEntity));
        }

        List<RefreshTokenEntity> refreshTokenEntities = userEntity.getRefreshTokenEntities();
        List<RefreshTokenResponse> refreshTokenResponses = new ArrayList<>();
        for(RefreshTokenEntity refreshTokenEntity : refreshTokenEntities) {
            refreshTokenResponses.add(refreshTokenConvert
                    .convertToRefreshTokenResponse(refreshTokenEntity));
        }
        userResponse.setImages(imageResponses);
        userResponse.setRefreshTokens(refreshTokenResponses);
        return userResponse;
    }
}
