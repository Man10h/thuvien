package com.web.thuvien.service;

import com.web.thuvien.model.dto.ImageDTO;
import com.web.thuvien.model.response.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    public List<ImageResponse> getImages(Long bookId);
    public ImageResponse getImageById(Long imageId);
    public String addImage(ImageDTO imageDTO);
    public String deleteImage(Long imageId);
}
