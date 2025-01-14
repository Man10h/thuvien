package com.web.thuvien.convert;

import com.web.thuvien.model.entity.ImageEntity;
import com.web.thuvien.model.response.ImageResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageConvert {

    @Autowired
    private ModelMapper modelMapper;

    public ImageResponse convertToImageResponse(ImageEntity imageEntity) {
        return modelMapper.map(imageEntity, ImageResponse.class);
    }
}
