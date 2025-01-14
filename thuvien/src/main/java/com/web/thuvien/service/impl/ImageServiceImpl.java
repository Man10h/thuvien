package com.web.thuvien.service.impl;

import com.web.thuvien.convert.ImageConvert;
import com.web.thuvien.exception.ex.BookNotFoundException;
import com.web.thuvien.exception.ex.ImageNotFoundException;
import com.web.thuvien.model.dto.ImageDTO;
import com.web.thuvien.model.entity.BookEntity;
import com.web.thuvien.model.entity.ImageEntity;
import com.web.thuvien.model.response.ImageResponse;
import com.web.thuvien.repository.BookRepository;
import com.web.thuvien.repository.ImageRepository;
import com.web.thuvien.service.CloudinaryService;
import com.web.thuvien.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageConvert imageConvert;

    @Autowired
    private BookRepository bookRepository;

    @Transactional(readOnly = true)
    public List<ImageResponse> getImages(Long bookId) {
        List<ImageEntity> imageEntities = imageRepository.findByBookEntity_Id(bookId);
        if(imageEntities.isEmpty()){
            return null;
        }
        return imageEntities.stream().map(it -> imageConvert.convertToImageResponse(it)).toList();
    }

    @Transactional(readOnly = true)
    public ImageResponse getImageById(Long imageId) {
        Optional<ImageEntity> optional = imageRepository.findById(imageId);
        if(optional.isEmpty()){
            throw new ImageNotFoundException("Image not found");
        }
        ImageEntity imageEntity = optional.get();
        return imageConvert.convertToImageResponse(imageEntity);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public String addImage(ImageDTO imageDTO) {
        if(imageDTO.getBookId() == null || imageDTO.getImages().isEmpty()){
            return null;
        }
        Optional<BookEntity> optional = bookRepository.findById(imageDTO.getBookId());
        if(optional.isEmpty()){
            throw new BookNotFoundException("Book not found");
        }
        BookEntity bookEntity = optional.get();
        for(MultipartFile multipartFile : imageDTO.getImages()){
            Map result = cloudinaryService.upload(multipartFile);
            ImageEntity imageEntity = ImageEntity.builder()
                    .bookEntity(bookEntity)
                    .fileId((String) result.get("public_id"))
                    .fileName((String) result.get("original_filename"))
                    .fileUrl((String)result.get("url"))
                    .build();
            imageEntity.setBookEntity(bookEntity);
            imageRepository.save(imageEntity);
        }
        return "Add image successfully";
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public String deleteImage(Long imageId) {
        Optional<ImageEntity> optional = imageRepository.findById(imageId);
        if(optional.isEmpty()){
            throw new ImageNotFoundException("Image not found");
        }
        imageRepository.deleteById(imageId);
        return "Delete image successfully";
    }


}
